package com.may.stock.service

class TransactionStockService(
    private val stockService: StockService
) {
    fun decrease(id: Long, quantity: Long) {
        startTransaction()
        stockService.decrease(id, quantity)
        endTransaction()
    }

    fun startTransaction() {
    }

    fun endTransaction() {
    }
}
