package com.may.stock.facade

import com.may.stock.domain.Stock
import com.may.stock.repository.StockRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
internal class LettuceLockStockFacadeTest(
    @Autowired
    private val lettuceLockStockFacade: LettuceLockStockFacade,
    @Autowired
    private val stockRepository: StockRepository
) {
    @BeforeEach
    fun insert() {
        val stock = Stock(
            productId = 1L,
            quantity = 100L
        )
        stockRepository.saveAndFlush<Stock>(stock)
        println("save And Flush 성공")
    }

    @AfterEach
    fun delete() {
        stockRepository.deleteAll()
    }

    @Test
    fun `동시에_100명이_주문`() {
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)
        for (i in 0 until threadCount) {
            executorService.submit {
                try {
                    lettuceLockStockFacade.decrease(1L, 1L)
                    println("decrease")
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    latch.countDown()
                    println("countDown")
                }
            }
        }
        latch.await()
        val stock = stockRepository.findById(1L).orElseThrow()

        // 100 - (100 * 1) = 0
        assertEquals(0, stock.quantity)
    }
}
