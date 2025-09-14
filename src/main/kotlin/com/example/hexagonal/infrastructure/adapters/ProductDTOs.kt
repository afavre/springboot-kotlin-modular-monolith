package com.example.hexagonal.infrastructure.adapters

import com.example.hexagonal.domain.Product
import com.example.hexagonal.domain.ProductCategory
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Request DTO for creating a product
 */
data class CreateProductRequest(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val category: ProductCategory
)

/**
 * Request DTO for updating product price
 */
data class UpdatePriceRequest(
    val price: BigDecimal
)

/**
 * Response DTO for product data
 */
data class ProductResponse(
    val id: String,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val category: ProductCategory,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isExpensive: Boolean
) {
    companion object {
        fun from(product: Product): ProductResponse = ProductResponse(
            id = product.id.value.toString(),
            name = product.name,
            description = product.description,
            price = product.price,
            category = product.category,
            createdAt = product.createdAt,
            updatedAt = product.updatedAt,
            isExpensive = product.isExpensive()
        )
    }
}