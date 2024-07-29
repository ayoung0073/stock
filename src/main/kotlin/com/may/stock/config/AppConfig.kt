package com.may.stock.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun transactionManager(emf: EntityManagerFactory): CustomJpaTransactionManager {
        return CustomJpaTransactionManager(emf)
    }
}
