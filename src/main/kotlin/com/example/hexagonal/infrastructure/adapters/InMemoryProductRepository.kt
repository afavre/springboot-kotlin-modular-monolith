package com.example.hexagonal.infrastructure.adapters

import com.example.hexagonal.application.ports.ProductRepository
import com.example.hexagonal.domain.Product
import com.example.hexagonal.domain.ProductCategory
import com.example.hexagonal.domain.ProductId
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

/**
 * In-memory implementation of ProductRepository.
 * This is an adapter in the infrastructure layer that implements the port defined
 * in the application layer.
 */
@Repository
class InMemoryProductRepository : ProductRepository {

    private val products = ConcurrentHashMap<ProductId, Product>()

    override fun save(product: Product): Product {
        products[product.id] = product
        return product
    }

    override fun findById(id: ProductId): Product? {
        return products[id]
    }

    override fun findAll(): List<Product> {
        return products.values.toList()
    }

    override fun deleteById(id: ProductId): Boolean {
        return products.remove(id) != null
    }

    override fun existsById(id: ProductId): Boolean {
        return products.containsKey(id)
    }

    override fun findByCategory(category: String): List<Product> {
        return products.values.filter { it.category.name == category }
    }
}