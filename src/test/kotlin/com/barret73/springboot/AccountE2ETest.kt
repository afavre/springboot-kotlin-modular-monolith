package com.barret73.springboot

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AccountE2ETest : FunSpec() {

    @Autowired private lateinit var mockMvc: MockMvc

    init {
        extension(SpringExtension)

        test(
            "E2E Account - Given a new account, when created, then it can be retrieved and deleted"
        ) {
            // Create an account
            val createResult =
                mockMvc
                    .post("/accounts") {
                        contentType = MediaType.APPLICATION_JSON
                        content =
                            """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com"
                }
            """
                                .trimIndent()
                    }
                    .andDo { println() }
                    .andExpect {
                        status { isCreated() }
                        jsonPath("$.firstName") { value("John") }
                        jsonPath("$.lastName") { value("Doe") }
                        jsonPath("$.email") { value("john.doe@example.com") }
                        jsonPath("$.id") { exists() }
                    }
                    .andReturn()

            val createdId =
                jacksonObjectMapper()
                    .readTree(createResult.response.contentAsString)
                    .get("id")
                    .asText()

            // Retrieve the account
            mockMvc.get("/accounts/$createdId").andExpect {
                status { isOk() }
                jsonPath("$.firstName") { value("John") }
                jsonPath("$.lastName") { value("Doe") }
                jsonPath("$.email") { value("john.doe@example.com") }
                jsonPath("$.id") { value(createdId) }
            }

            // Delete the account
            mockMvc.delete("/accounts/$createdId").andExpect { status { isNoContent() } }

            // Try to retrieve again, expect 404
            mockMvc.get("/accounts/$createdId").andExpect { status { isNotFound() } }
        }
    }
}
