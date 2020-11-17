package org.template.core.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.text.MessageFormat;

@Configuration
@ConditionalOnProperty(name = {"datasource.name", "datasource.username", "datasource.password", "datasource.name"})
@ConditionalOnClass(DataSource.class)
public class DataSourceBean {

    @Value("${datasource.host}")
    private String dbHost;

    @Value("${datasource.username}")
    private String dbUser;

    @Value("${datasource.password}")
    private String dbPassword;

    @Value("${datasource.name}")
    private String dbName;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(MessageFormat.format("jdbc:postgresql://{0}/{1}", dbHost, dbName));
        dataSourceBuilder.username(dbUser.trim());
        dataSourceBuilder.password(dbPassword.trim());
        return dataSourceBuilder.build();
    }
}
