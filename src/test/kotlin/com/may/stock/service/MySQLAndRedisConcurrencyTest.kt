package com.may.stock.service

import com.may.stock.repository.StockRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.Executors
import java.util.concurrent.Future

@SpringBootTest
internal class MySQLAndRedisConcurrencyTest(
    @Autowired
    private val stockRepository: StockRepository,
    @Autowired
    private val redisTemplate: RedisTemplate<String, String>,
    @Autowired
    private val stockAndRedisService: StockAndRedisService,
) {
    private val logger = LoggerFactory.getLogger(MySQLAndRedisConcurrencyTest::class.java)

    @BeforeEach
    fun before() {
        redisTemplate.opsForZSet().remove("test", "1")
        stockRepository.deleteAll()
    }

    @Test
    fun `테스트`() {
        val threads = Executors.newFixedThreadPool(30)
        val tasks = mutableListOf<Future<*>>()

        for (i in 1..5) {
            tasks.add(threads.submit(stockAndRedisService::execute))
        }

        // 모든 작업이 완료되기를 기다림
        tasks.forEach { it.get() }

        val score = redisTemplate.opsForZSet().score("test", "1")
        assertEquals(1.0, score)
    }
}
