package com.example.hexagonal

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import io.kotest.core.spec.style.BehaviorSpec

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = ["spring.profiles.active=test"])
class HexagonalApplicationTest : BehaviorSpec({

    given("the Spring Boot application") {
        `when`("the context loads") {
            then("it should start successfully") {
                // This test verifies that the Spring context can load properly
                // The @SpringBootTest annotation will start the application
                // and fail if there are any configuration issues
            }
        }
    }
})