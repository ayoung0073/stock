package com.may.stock.service

import com.may.stock.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PessimisticLockStockService(
    private val stockRepository: StockRepository
) {

    @Transactional
    fun decrease(id: Long, quantity: Long) {
        val stock = stockRepository.findByIdWithPessimisticLock(id) // Lock을 걸고 데이터를 가지고 온 후
        stock.decrease(quantity) // 재고 감소 시킨 후

        stockRepository.saveAndFlush(stock) // 재고 저장
    }
}
