package com.may.stock.repository

import com.may.stock.domain.StockDocument
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class MongoStockRepository(
    private val mongoTemplate: MongoTemplate
) {
    /*
        몽고는 document에 수행하는 모든 명령어가 원자성을 보장한다.
     */
    fun incQuantity(id: Long, quantity: Long) {
        mongoTemplate.findAndModify(
            Query(
                Criteria("_id").`is`(id).and("quantity").gt(0)
            ),
            Update().inc("quantity", quantity),
            StockDocument::class.java
        )
    }

    fun save(stock: StockDocument) {
        mongoTemplate.save(
            stock
        )
    }

    fun findById(id: Long): StockDocument? {
        return mongoTemplate.findOne(
            Query(
                Criteria("_id").`is`(id)
            ),
            StockDocument::class.java
        )
    }
}
