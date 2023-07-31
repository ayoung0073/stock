package com.may.stock.service

import com.may.stock.domain.StockDocument
import com.may.stock.repository.MongoStockRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
internal class MongoStockServiceTest(
    @Autowired
    private val stockMongoService: MongoStockService,
    @Autowired
    private val mongoStockRepository: MongoStockRepository
) {
    @BeforeEach
    fun insert() {
        val stock = StockDocument(
            id = 1L,
            productId = 1L,
            quantity = 100L
        )
        mongoStockRepository.save(stock)
    }

    @Test
    fun `decrease 테스트`() {
        stockMongoService.decrease(1L, 1L)
        val stock = mongoStockRepository.findById(1L)
        // 100 - 1 = 99
        assertEquals(99, stock?.quantity)
    }

    @Test
    fun `동시에_100명이_주문`() {
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)
        for (i in 0 until threadCount) {
            executorService.submit {
                try {
                    stockMongoService.decrease(1L, 1L)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()
        val stock = mongoStockRepository.findById(1L)

        // 100 - (100 * 1) = 0
        assertEquals(0, stock?.quantity)
    }
}
