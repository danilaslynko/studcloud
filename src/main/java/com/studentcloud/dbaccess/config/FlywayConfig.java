package com.studentcloud.dbaccess.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations("classpath:\\migration");
        flyway.setDataSource(
                "jdbc:mysql://localhost:3306/studcloud_db?useUnicode=true&serverTimezone=UTC",
                "root",
                "1Qwerty1");
        return flyway;
    }
}
