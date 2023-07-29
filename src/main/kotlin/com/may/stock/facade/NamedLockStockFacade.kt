package com.may.stock.facade

import com.may.stock.repository.LockRepository
import com.may.stock.service.StockService
import org.springframework.stereotype.Service

@Service
class NamedLockStockFacade(
    private val lockRepository: LockRepository,
    private val stockService: StockService,
) {

    fun decrease(id: Long, quantity: Long) {
        try {
            lockRepository.getLock(id.toString())
            stockService.decrease(id, quantity)
        } finally {
            lockRepository.releaseLock(id.toString())
        }
    }

}