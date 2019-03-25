package com.springboot.template.security;

import com.springboot.template.security.filter.AuthorizationFilter;
import com.springboot.template.security.filter.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationService authenticationService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SecurityParams securityParams;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    public SecurityConfig(AuthenticationService authenticationService, BCryptPasswordEncoder bCryptPasswordEncoder, SecurityParams securityParams) {
        this.authenticationService = authenticationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.securityParams = securityParams;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)).and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/swagger", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/swagger-ui.html", "/csrf", "/").permitAll()
                .antMatchers("/health").permitAll()
                .antMatchers("/health/protected").authenticated()
                .antMatchers("/health/protected/roles").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .addFilter(new LoginFilter(authenticationManager(), securityParams))
                .addFilter(new AuthorizationFilter(authenticationManager(), securityParams))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(bCryptPasswordEncoder);
    }
}
