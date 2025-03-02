package com.reservaki.reservaki.integration;


import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseIntegrationTest {

    private static PostgreSQLContainer<?> postgres;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (postgres != null) {
                postgres.stop();
            }
        }));
    }

    protected static synchronized PostgreSQLContainer<?> getPostgresContainer() {
        if (postgres == null) {
            postgres = new PostgreSQLContainer<>("postgres:14-alpine")
                    .withDatabaseName("reservaki_test")
                    .withUsername("test")
                    .withPassword("test")
                    .withReuse(true);
            postgres.start();
        }
        return postgres;
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        PostgreSQLContainer<?> container = getPostgresContainer();
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.flyway.url", container::getJdbcUrl);
        registry.add("spring.flyway.user", container::getUsername);
        registry.add("spring.flyway.password", container::getPassword);
    }
}