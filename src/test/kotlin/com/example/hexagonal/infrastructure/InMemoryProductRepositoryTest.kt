package com.example.hexagonal.infrastructure.adapters

import com.example.hexagonal.domain.Product
import com.example.hexagonal.domain.ProductCategory
import com.example.hexagonal.domain.ProductId
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.math.BigDecimal
import java.time.LocalDateTime

class InMemoryProductRepositoryTest : BehaviorSpec({

    given("an in-memory product repository") {

        `when`("saving a product") {
            val repository = InMemoryProductRepository()
            val product = Product(
                id = ProductId.generate(),
                name = "Test Product",
                description = "Test Description",
                price = BigDecimal("99.99"),
                category = ProductCategory.ELECTRONICS,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )

            val savedProduct = repository.save(product)

            then("it should return the saved product") {
                savedProduct shouldBe product
            }

            then("it should be findable by ID") {
                repository.findById(product.id) shouldBe product
            }

            then("it should exist") {
                repository.existsById(product.id) shouldBe true
            }
        }

        `when`("finding all products in an empty repository") {
            val repository = InMemoryProductRepository()
            
            then("it should return an empty list") {
                repository.findAll().shouldBeEmpty()
            }
        }

        `when`("saving multiple products") {
            val repository = InMemoryProductRepository()
            val product1 = Product(
                id = ProductId.generate(),
                name = "Product 1",
                description = "Description 1",
                price = BigDecimal("50.00"),
                category = ProductCategory.BOOKS,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )

            val product2 = Product(
                id = ProductId.generate(),
                name = "Product 2",
                description = "Description 2",
                price = BigDecimal("75.00"),
                category = ProductCategory.ELECTRONICS,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )

            repository.save(product1)
            repository.save(product2)

            then("findAll should return both products") {
                val allProducts = repository.findAll()
                allProducts shouldHaveSize 2
                allProducts shouldContain product1
                allProducts shouldContain product2
            }

            then("finding by category should work") {
                val booksProducts = repository.findByCategory("BOOKS")
                booksProducts shouldHaveSize 1
                booksProducts shouldContain product1

                val electronicsProducts = repository.findByCategory("ELECTRONICS")
                electronicsProducts shouldHaveSize 1
                electronicsProducts shouldContain product2
            }
        }

        `when`("deleting a product") {
            val repository = InMemoryProductRepository()
            val product = Product(
                id = ProductId.generate(),
                name = "Product to Delete",
                description = "Description",
                price = BigDecimal("25.00"),
                category = ProductCategory.CLOTHING,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )

            repository.save(product)
            val deleted = repository.deleteById(product.id)

            then("it should return true") {
                deleted shouldBe true
            }

            then("the product should no longer exist") {
                repository.existsById(product.id) shouldBe false
                repository.findById(product.id) shouldBe null
            }
        }

        `when`("deleting a non-existing product") {
            val repository = InMemoryProductRepository()
            val nonExistingId = ProductId.generate()

            then("it should return false") {
                repository.deleteById(nonExistingId) shouldBe false
            }
        }

        `when`("finding by non-existing ID") {
            val repository = InMemoryProductRepository()
            val nonExistingId = ProductId.generate()

            then("it should return null") {
                repository.findById(nonExistingId) shouldBe null
            }

            then("existsById should return false") {
                repository.existsById(nonExistingId) shouldBe false
            }
        }
    }
})