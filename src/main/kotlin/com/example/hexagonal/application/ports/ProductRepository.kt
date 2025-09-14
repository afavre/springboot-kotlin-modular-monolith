package com.example.hexagonal.application.ports

import com.example.hexagonal.domain.Product
import com.example.hexagonal.domain.ProductId

/**
 * Port (interface) defining the contract for product persistence.
 * This is part of the application layer and defines what the domain needs
 * from the outside world without knowing how it's implemented.
 */
interface ProductRepository {
    
    /**
     * Save a product to the repository
     */
    fun save(product: Product): Product
    
    /**
     * Find a product by its ID
     */
    fun findById(id: ProductId): Product?
    
    /**
     * Find all products
     */
    fun findAll(): List<Product>
    
    /**
     * Delete a product by its ID
     */
    fun deleteById(id: ProductId): Boolean
    
    /**
     * Check if a product exists by its ID
     */
    fun existsById(id: ProductId): Boolean
    
    /**
     * Find products by category
     */
    fun findByCategory(category: String): List<Product>
}