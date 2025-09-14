package com.example.hexagonal.application.services

import com.example.hexagonal.application.ports.ProductRepository
import com.example.hexagonal.domain.Product
import com.example.hexagonal.domain.ProductCategory
import com.example.hexagonal.domain.ProductId
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import java.time.LocalDateTime

class ProductServiceTest : BehaviorSpec({

    given("a product service with mocked repository") {

        `when`("creating a new product") {
            val mockRepository = mockk<ProductRepository>()
            val productService = ProductService(mockRepository)
            val productName = "Test Product"
            val description = "Test Description"
            val price = BigDecimal("99.99")
            val category = ProductCategory.ELECTRONICS

            every { mockRepository.save(any()) } answers { firstArg() }

            val result = productService.createProduct(productName, description, price, category)

            then("it should save the product and return it") {
                result.name shouldBe productName
                result.description shouldBe description
                result.price shouldBe price
                result.category shouldBe category
                result.id shouldNotBe null
                verify { mockRepository.save(any()) }
            }
        }

        `when`("getting a product by ID") {
            val mockRepository = mockk<ProductRepository>()
            val productService = ProductService(mockRepository)
            val productId = ProductId.generate()
            val product = Product(
                id = productId,
                name = "Test Product",
                description = "Description",
                price = BigDecimal("50.00"),
                category = ProductCategory.BOOKS,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )

            every { mockRepository.findById(productId) } returns product

            val result = productService.getProduct(productId)

            then("it should return the product") {
                result shouldBe product
                verify { mockRepository.findById(productId) }
            }
        }

        `when`("updating a product price") {
            val mockRepository = mockk<ProductRepository>()
            val productService = ProductService(mockRepository)
            val productId = ProductId.generate()
            val originalProduct = Product(
                id = productId,
                name = "Test Product",
                description = "Description",
                price = BigDecimal("50.00"),
                category = ProductCategory.CLOTHING,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
            val newPrice = BigDecimal("75.00")

            every { mockRepository.findById(productId) } returns originalProduct
            every { mockRepository.save(any()) } answers { firstArg() }

            val result = productService.updateProductPrice(productId, newPrice)

            then("it should update and save the product") {
                result?.price shouldBe newPrice
                result?.updatedAt shouldNotBe originalProduct.updatedAt
                verify { mockRepository.findById(productId) }
                verify { mockRepository.save(any()) }
            }
        }

        `when`("deleting an existing product") {
            val mockRepository = mockk<ProductRepository>()
            val productService = ProductService(mockRepository)
            val productId = ProductId.generate()

            every { mockRepository.existsById(productId) } returns true
            every { mockRepository.deleteById(productId) } returns true

            val result = productService.deleteProduct(productId)

            then("it should delete the product and return true") {
                result shouldBe true
                verify { mockRepository.existsById(productId) }
                verify { mockRepository.deleteById(productId) }
            }
        }

        `when`("deleting a non-existing product") {
            val mockRepository = mockk<ProductRepository>()
            val productService = ProductService(mockRepository)
            val productId = ProductId.generate()

            every { mockRepository.existsById(productId) } returns false

            val result = productService.deleteProduct(productId)

            then("it should return false") {
                result shouldBe false
                verify { mockRepository.existsById(productId) }
                verify(exactly = 0) { mockRepository.deleteById(any()) }
            }
        }
    }
})