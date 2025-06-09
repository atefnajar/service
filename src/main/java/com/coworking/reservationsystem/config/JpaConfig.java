package com.coworking.reservationsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.coworking.reservationsystem.repository")
@EnableTransactionManagement
public class JpaConfig {
    // Configuration JPA pour éviter les problèmes avec les records Java
}