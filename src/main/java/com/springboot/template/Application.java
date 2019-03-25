package com.springboot.template;

import com.springboot.template.core.security.SecurityParams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityParams jwtSecurityConfig() {
        return new SecurityParams();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
