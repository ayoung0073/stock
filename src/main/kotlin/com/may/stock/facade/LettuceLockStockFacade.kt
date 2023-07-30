package com.may.stock.facade

import com.may.stock.repository.RedisLockRepository
import com.may.stock.service.StockService
import org.springframework.stereotype.Component

@Component
class LettuceLockStockFacade(
    private val redisLockRepository: RedisLockRepository,
    private val stockService: StockService
) {
    fun decrease(key: Long, quantity: Long) {
        while (!redisLockRepository.lock(key.toString())) {
            Thread.sleep(150)
        }
        try {
            stockService.decrease(key, quantity)
        } finally {
            redisLockRepository.unlock(key.toString())
        }
    }
}
