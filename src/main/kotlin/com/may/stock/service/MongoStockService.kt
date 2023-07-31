package com.may.stock.service

import com.may.stock.repository.MongoStockRepository
import org.springframework.stereotype.Service

@Service
class MongoStockService(
    private val mongoStockRepository: MongoStockRepository
) {
    fun decrease(id: Long, quantity: Long) {
        mongoStockRepository.incQuantity(id, -quantity)
    }
}
