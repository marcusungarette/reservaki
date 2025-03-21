package com.reservaki.reservaki.infrastructure.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class FlywayConfig {

    @Bean
    @ConditionalOnProperty(name = "spring.flyway.enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnBean(Flyway.class)
    public CommandLineRunner flywayRunner(Flyway flyway) {
        return args -> {
            log.info("Checking Flyway migration files...");
            try {
                var migrations = flyway.info().all();
                log.info("Found {} migration files:", migrations.length);
                for (var migration : migrations) {
                    log.info("Migration: {} - {} - {}",
                            migration.getVersion(),
                            migration.getDescription(),
                            migration.getState());
                }
                var result = flyway.migrate();
                log.info("Successfully applied {} migrations", result.migrationsExecuted);

            } catch (Exception e) {
                log.error("Error checking/applying migrations", e);
                throw e;
            }
        };
    }
}