package com.barret73.springboot.accounts.domain

import com.barret73.springboot.accounts.api.dto.AccountDto
import com.barret73.springboot.accounts.api.dto.toDomain
import com.barret73.springboot.domain.Account
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AccountServiceTest :
    FunSpec({
        test(
            "Given an account with a non existing email, when createAccount is called, then it should create the account and generate an ID"
        ) {
            val idGenerator = mockk<IdGenerator>()
            val repository = mockk<AccountRepository>()

            val email = "a@b.com"

            val dto = AccountDto(email = email, firstName = "A", lastName = "B")
            val expectedId = "test-id"
            val expectedAccount: Account = dto.toDomain().copy(id = expectedId)
            dto.toDomain()

            every { repository.existsByEmail(email) } returns false
            every { idGenerator.generateId() } returns expectedId
            every { repository.save(expectedAccount) } answers {}

            val service = AccountService(idGenerator = idGenerator, repo = repository)
            val account = service.createAccount(dto.toDomain())

            account shouldBe expectedAccount
            verify { idGenerator.generateId() }
            verify { repository.save(expectedAccount) }
        }

        test(
            "Given an account with an existing email, when createAccount is called, then it should throw an exception"
        ) {
            val idGenerator = mockk<IdGenerator>()
            val repository = mockk<AccountRepository>()
            val email = "a@b.com"
            val dto = AccountDto(email = email, firstName = "A", lastName = "B")

            every { repository.existsByEmail(email) } returns true

            val service = AccountService(idGenerator = idGenerator, repo = repository)

            shouldThrow<IllegalArgumentException> { service.createAccount(dto.toDomain()) }

            verify { repository.existsByEmail(email) }
            verify(exactly = 0) { repository.save(any()) }
            verify(exactly = 0) { idGenerator.generateId() }
        }
    })
