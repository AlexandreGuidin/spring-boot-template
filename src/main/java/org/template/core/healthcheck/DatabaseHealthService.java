package org.template.core.healthcheck;


import org.template.core.healthcheck.model.HealthStatus;
import org.template.core.healthcheck.model.HealthStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
@ConditionalOnProperty(name = "datasource.name")
@ConditionalOnClass(DataSource.class)
public class DatabaseHealthService implements HealthService {

    private static final Logger log = LoggerFactory.getLogger(DatabaseHealthService.class);
    private static final String SERVICE_NAME = "database";
    private final DataSource dataSource;

    @Autowired
    public DatabaseHealthService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public HealthStatus execute() {
        try (Connection connection = dataSource.getConnection()) {
            connection.getCatalog();
            return new HealthStatus(SERVICE_NAME, HealthStatusEnum.OK);
        } catch (Exception e) {
            log.error("error while executing database health check", e);
            return new HealthStatus(SERVICE_NAME, HealthStatusEnum.UNAVAILABLE).addDetail("connection_error", e.getMessage());
        }
    }
}
