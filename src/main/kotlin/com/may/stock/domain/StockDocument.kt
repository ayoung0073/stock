package com.may.stock.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class StockDocument(
    @Id
    val id: Long,
    var productId: Long,
    var quantity: Long,
) {
    fun decrease(quantity: Long) {
        if (this.quantity - quantity < 0) {
            throw RuntimeException("foo")
        }
        this.quantity = this.quantity - quantity
    }
}
