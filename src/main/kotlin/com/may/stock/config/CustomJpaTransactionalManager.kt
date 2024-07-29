package com.may.stock.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.support.DefaultTransactionStatus

class CustomJpaTransactionManager(emf: EntityManagerFactory) : JpaTransactionManager(emf) {
    override fun doCommit(status: DefaultTransactionStatus) {
        logger.info("Committing transaction")
        super.doCommit(status)
    }
}
