package com.reservaki.reservaki.infrastructure.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class FlywayConfig {

    @Bean
    public CommandLineRunner flywayRunner(Flyway flyway) {
        return args -> {
            log.info("Checking Flyway migration files...");
            try {
                // Lista todas as migrações disponíveis
                var migrations = flyway.info().all();
                log.info("Found {} migration files:", migrations.length);
                for (var migration : migrations) {
                    log.info("Migration: {} - {} - {}",
                            migration.getVersion(),
                            migration.getDescription(),
                            migration.getState());
                }

                // Tenta executar as migrações
                var result = flyway.migrate();
                log.info("Successfully applied {} migrations", result.migrationsExecuted);

            } catch (Exception e) {
                log.error("Error checking/applying migrations", e);
                throw e;
            }
        };
    }
}