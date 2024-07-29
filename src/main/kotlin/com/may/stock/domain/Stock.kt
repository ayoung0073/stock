package com.may.stock.domain

import jakarta.persistence.*

@Entity
class Stock(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0,

    @Column(unique = true)
    var productId: Long,

    var quantity: Long,

    @Version
    val version: Long? = null
) {
    fun decrease(quantity: Long) {
        if (this.quantity - quantity < 0) {
            throw RuntimeException("foo")
        }
        this.quantity = this.quantity - quantity
    }
}
