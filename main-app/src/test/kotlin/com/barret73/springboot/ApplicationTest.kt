package com.barret73.springboot

import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = ["spring.profiles.active=test"])
class ApplicationTest :
    BehaviorSpec({
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
