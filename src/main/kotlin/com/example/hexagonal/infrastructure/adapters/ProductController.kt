package com.example.hexagonal.infrastructure.adapters

import com.example.hexagonal.application.services.ProductService
import com.example.hexagonal.domain.ProductCategory
import com.example.hexagonal.domain.ProductId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

/**
 * REST controller adapter that exposes the application services via HTTP.
 * This is part of the infrastructure layer and adapts HTTP requests to application service calls.
 */
@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping
    fun createProduct(@RequestBody request: CreateProductRequest): ResponseEntity<ProductResponse> {
        val product = productService.createProduct(
            name = request.name,
            description = request.description,
            price = request.price,
            category = request.category
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponse.from(product))
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: String): ResponseEntity<ProductResponse> {
        val productId = try {
            ProductId.fromString(id)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().build()
        }
        
        val product = productService.getProduct(productId)
        return if (product != null) {
            ResponseEntity.ok(ProductResponse.from(product))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<ProductResponse>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products.map { ProductResponse.from(it) })
    }

    @PutMapping("/{id}/price")
    fun updateProductPrice(
        @PathVariable id: String,
        @RequestBody request: UpdatePriceRequest
    ): ResponseEntity<ProductResponse> {
        val productId = try {
            ProductId.fromString(id)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().build()
        }
        
        val updatedProduct = productService.updateProductPrice(productId, request.price)
        return if (updatedProduct != null) {
            ResponseEntity.ok(ProductResponse.from(updatedProduct))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: String): ResponseEntity<Unit> {
        val productId = try {
            ProductId.fromString(id)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().build()
        }
        
        val deleted = productService.deleteProduct(productId)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/category/{category}")
    fun getProductsByCategory(@PathVariable category: String): ResponseEntity<List<ProductResponse>> {
        val productCategory = try {
            ProductCategory.valueOf(category.uppercase())
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().build()
        }
        
        val products = productService.getProductsByCategory(productCategory)
        return ResponseEntity.ok(products.map { ProductResponse.from(it) })
    }

    @GetMapping("/expensive")
    fun getExpensiveProducts(
        @RequestParam(defaultValue = "100.00") threshold: BigDecimal
    ): ResponseEntity<List<ProductResponse>> {
        val products = productService.getExpensiveProducts(threshold)
        return ResponseEntity.ok(products.map { ProductResponse.from(it) })
    }
}