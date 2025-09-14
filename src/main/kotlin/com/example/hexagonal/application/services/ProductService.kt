package com.example.hexagonal.application.services

import com.example.hexagonal.application.ports.ProductRepository
import com.example.hexagonal.domain.Product
import com.example.hexagonal.domain.ProductCategory
import com.example.hexagonal.domain.ProductId
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Application service that orchestrates business operations.
 * This is part of the application layer and contains use cases/business workflows.
 */
@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    /**
     * Create a new product
     */
    fun createProduct(
        name: String,
        description: String,
        price: BigDecimal,
        category: ProductCategory
    ): Product {
        val now = LocalDateTime.now()
        val product = Product(
            id = ProductId.generate(),
            name = name,
            description = description,
            price = price,
            category = category,
            createdAt = now,
            updatedAt = now
        )
        return productRepository.save(product)
    }

    /**
     * Get a product by ID
     */
    fun getProduct(id: ProductId): Product? {
        return productRepository.findById(id)
    }

    /**
     * Get all products
     */
    fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    /**
     * Update product price
     */
    fun updateProductPrice(id: ProductId, newPrice: BigDecimal): Product? {
        val product = productRepository.findById(id)
        return product?.let {
            val updatedProduct = it.updatePrice(newPrice)
            productRepository.save(updatedProduct)
        }
    }

    /**
     * Delete a product
     */
    fun deleteProduct(id: ProductId): Boolean {
        return if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
        } else {
            false
        }
    }

    /**
     * Get products by category
     */
    fun getProductsByCategory(category: ProductCategory): List<Product> {
        return productRepository.findByCategory(category.name)
    }

    /**
     * Get expensive products (business logic)
     */
    fun getExpensiveProducts(threshold: BigDecimal = BigDecimal("100.00")): List<Product> {
        return productRepository.findAll().filter { it.isExpensive(threshold) }
    }
}