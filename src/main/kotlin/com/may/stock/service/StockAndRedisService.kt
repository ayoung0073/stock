package com.may.stock.service

import com.may.stock.domain.Stock
import com.may.stock.repository.StockRepository
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockAndRedisService(
    private val stockRepository: StockRepository,
    private val redisTemplate: RedisTemplate<String, String>,
) {
    private val logger = LoggerFactory.getLogger(StockAndRedisService::class.java)

    @Transactional
    fun execute() {
        val stock = Stock(
            productId = 1L,
            quantity = 100L
        )
        stockRepository.save(stock)
        logger.info("after save")

        redisTemplate.opsForZSet().incrementScore("test", stock.productId.toString(), 1.0)
        logger.info("method finish")
    }
}
