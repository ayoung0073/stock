package com.may.stock.facade

import com.may.stock.service.StockService
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedissonLockStockFacade(
    private val redissonClient: RedissonClient,
    private val stockService: StockService
) {
    fun decrease(key: Long, quantity: Long) {
        val lock: RLock = redissonClient.getLock(key.toString())
        try {
            val available = lock.tryLock(10, 1, TimeUnit.SECONDS)
            println("available: $available")
            if (!available) {
                println("lock 획득 실패")
                return
            }
            stockService.decrease(key, quantity)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        } finally {
            lock.unlock()
        }
    }
}
