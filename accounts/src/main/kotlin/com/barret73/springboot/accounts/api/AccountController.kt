package com.barret73.springboot.accounts.api

import com.barret73.springboot.accounts.api.dto.AccountDto
import com.barret73.springboot.accounts.api.dto.toDomain
import com.barret73.springboot.accounts.config.logger
import com.barret73.springboot.accounts.domain.AccountService
import com.barret73.springboot.domain.Account
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountService: AccountService,
    private val accountValidator: AccountValidator,
) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(
        @Valid @RequestBody account: AccountDto,
    ): ResponseEntity<Account> {
        accountValidator.validate(account)
        logger.info { "Creating account for email=${account.email}" }
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(accountService.createAccount(account.toDomain()))
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: String,
    ): ResponseEntity<Account> {
        logger.info { "Fetching account by id=$id" }
        return accountService.getAccountById(id)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping
    fun getAll(): List<Account> {
        logger.info { "Fetching all accounts" }
        return accountService.getAllAccounts()
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody account: Account,
    ): ResponseEntity<Account> {
        logger.info { "Updating account id=$id" }
        return accountService.updateAccount(id, account)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: String,
    ): ResponseEntity<Unit> {
        logger.info { "Deleting account id=$id" }
        return if (accountService.deleteAccount(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
