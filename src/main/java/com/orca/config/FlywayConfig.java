package com.orca.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.flyway.*;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class FlywayConfig {

    @Autowired
    private Flyway flyway;

    @Bean
    public FlywayMigrationInitializer flywayInitializer() {
        return new FlywayMigrationInitializer(flyway, (f) -> {
            // Não faz nada inicialmente
        });
    }

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            // Estratégia vazia para evitar migração automática
        };
    }

    @Bean
    @DependsOn("entityManagerFactory")
    public ApplicationRunner flywayRunner() {
        return args -> {
            // Executa as migrações após o Hibernate ter criado as tabelas
            flyway.migrate();
        };
    }
}