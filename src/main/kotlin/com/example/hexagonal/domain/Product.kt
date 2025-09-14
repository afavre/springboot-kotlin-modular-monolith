package com.example.hexagonal.domain

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

/**
 * Core domain entity representing a Product.
 * This is part of the domain layer and contains business logic and rules.
 */
data class Product(
    val id: ProductId,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val category: ProductCategory,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    init {
        require(name.isNotBlank()) { "Product name cannot be blank" }
        require(price >= BigDecimal.ZERO) { "Product price must be non-negative" }
    }

    /**
     * Business method to update the product price
     */
    fun updatePrice(newPrice: BigDecimal): Product {
        require(newPrice >= BigDecimal.ZERO) { "Product price must be non-negative" }
        return copy(price = newPrice, updatedAt = LocalDateTime.now())
    }

    /**
     * Business method to check if the product is expensive
     */
    fun isExpensive(threshold: BigDecimal = BigDecimal("100.00")): Boolean {
        return price >= threshold
    }
}

/**
 * Value object for Product ID
 */
data class ProductId(val value: UUID) {
    companion object {
        fun generate(): ProductId = ProductId(UUID.randomUUID())
        fun fromString(value: String): ProductId = ProductId(UUID.fromString(value))
    }
    
    override fun toString(): String = value.toString()
}

/**
 * Enum representing product categories
 */
enum class ProductCategory {
    ELECTRONICS,
    CLOTHING,
    BOOKS,
    HOME_GARDEN,
    SPORTS,
    OTHER
}