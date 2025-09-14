package com.example.hexagonal.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.math.BigDecimal
import java.time.LocalDateTime

class ProductTest : BehaviorSpec({

    given("a valid product") {
        val product = Product(
            id = ProductId.generate(),
            name = "Test Product",
            description = "A test product",
            price = BigDecimal("50.00"),
            category = ProductCategory.ELECTRONICS,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        `when`("updating the price with a valid amount") {
            val newPrice = BigDecimal("75.00")
            val updatedProduct = product.updatePrice(newPrice)

            then("the price should be updated") {
                updatedProduct.price shouldBe newPrice
                updatedProduct.updatedAt shouldNotBe product.updatedAt
            }
        }

        `when`("checking if the product is expensive") {
            then("it should return false for the default threshold") {
                product.isExpensive() shouldBe false
            }

            then("it should return true for a lower threshold") {
                product.isExpensive(BigDecimal("25.00")) shouldBe true
            }
        }
    }

    given("invalid product data") {
        `when`("creating a product with blank name") {
            then("it should throw an exception") {
                shouldThrow<IllegalArgumentException> {
                    Product(
                        id = ProductId.generate(),
                        name = "",
                        description = "Description",
                        price = BigDecimal("50.00"),
                        category = ProductCategory.ELECTRONICS,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now()
                    )
                }
            }
        }

        `when`("creating a product with negative price") {
            then("it should throw an exception") {
                shouldThrow<IllegalArgumentException> {
                    Product(
                        id = ProductId.generate(),
                        name = "Test Product",
                        description = "Description",
                        price = BigDecimal("-10.00"),
                        category = ProductCategory.ELECTRONICS,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now()
                    )
                }
            }
        }

        `when`("updating price with negative amount") {
            val product = Product(
                id = ProductId.generate(),
                name = "Test Product",
                description = "Description",
                price = BigDecimal("50.00"),
                category = ProductCategory.ELECTRONICS,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )

            then("it should throw an exception") {
                shouldThrow<IllegalArgumentException> {
                    product.updatePrice(BigDecimal("-5.00"))
                }
            }
        }
    }
})