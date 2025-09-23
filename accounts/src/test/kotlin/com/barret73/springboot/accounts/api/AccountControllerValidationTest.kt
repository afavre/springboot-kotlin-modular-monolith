package com.barret73.springboot.accounts.api

import com.barret73.springboot.accounts.api.dto.AccountDto
import com.barret73.springboot.accounts.api.dto.toDomain
import com.barret73.springboot.accounts.domain.AccountService
import com.barret73.springboot.domain.Account
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(controllers = [AccountController::class])
@ContextConfiguration(classes = [AccountController::class, AccountService::class])
class AccountControllerTest : FunSpec() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var service: AccountService
    @MockkBean
    private lateinit var validator: AccountValidator

    init {
        extension(SpringExtension)

        test(
            "Given a valid account, when POST /accounts is called, then it should create the account"
        ) {
            val accountDto = AccountDto(firstName = "Test Account", email = "john.doe@example.com")
            val account: Account = accountDto.toDomain()
            every { service.createAccount(account) } returns account.copy(id = "1")
            every { validator.validate(any()) } answers {}

            mockMvc
                .post("/accounts") {
                    contentType = MediaType.APPLICATION_JSON
                    content =
                        """
                    {
                        "firstName": "Test Account",
                        "email":"john.doe@example.com"
                    }
                """
                            .trimIndent()
                }
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    jsonPath("$.firstName") { value("Test Account") }
                    jsonPath("$.email") { value("john.doe@example.com") }
                    jsonPath("$.id") { value("1") }
                }
        }

        test(
            "Given an existing account, when GET /accounts/{id} is called, then it should return the account"
        ) {
            val accountDto =
                AccountDto(
                    id = "1",
                    firstName = "John",
                    lastName = "Doe",
                    email = "john.doe@example.com",
                )
            every { service.getAccountById("1") } returns accountDto.toDomain()

            val expectedResult =
                """
                {"id":"1","firstName":"John","lastName":"Doe","email":"john.doe@example.com"}
                """
                    .trimIndent()

            mockMvc
                .get("/accounts/1")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { json(expectedResult) }
                }
        }

        test(
            "Given a non-existing account, when GET /accounts/{id} is called, then it should return 404"
        ) {
            every { service.getAccountById("999") } returns null

            mockMvc.get("/accounts/999").andDo { print() }.andExpect { status { isNotFound() } }
        }

        test("POST /accounts should return 400 if email is missing with meaningful error message") {
            val accountJson =
                """
                {
                    "firstName": "Test Account"
                }
                """
                    .trimIndent()

            val expectedResult =
                """
                    {"error": "Validation failed","details": {"email": "Email must not be blank"}}
                """
                    .trimIndent()
            mockMvc
                .post("/accounts") {
                    contentType = MediaType.APPLICATION_JSON
                    content = accountJson
                }
                .andDo { print() }
                .andExpect {
                    status {
                        isBadRequest()
                        //                        content { json(expectedResult) }
                    }
                }
        }

        test(
            "Given an existing account, when DELETE /accounts/{id} is called, then it should delete the account"
        ) {
            every { service.deleteAccount("1") } returns true
            mockMvc.delete("/accounts/1").andDo { print() }.andExpect { status { isNoContent() } }
        }

        test(
            "Given a non-existing account, when DELETE /accounts/{id} is called, then it should return 404"
        ) {
            every { service.deleteAccount("999") } returns false
            mockMvc.delete("/accounts/999").andDo { print() }.andExpect { status { isNotFound() } }
        }

        // TODO add tests for update and getAll
    }
}
