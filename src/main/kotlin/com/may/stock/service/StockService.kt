package com.may.stock.service

import com.may.stock.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
    private val stockRepository: StockRepository
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Synchronized
    fun decrease(id: Long, quantity: Long) {
        val stock = stockRepository.findById(id).orElseThrow()
        stock.decrease(quantity)
        stockRepository.saveAndFlush(stock)
    }
}