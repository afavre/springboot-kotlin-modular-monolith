package com.barret73.springboot.accounts.domain

import com.barret73.springboot.domain.Account
import io.github.oshai.kotlinlogging.KotlinLogging

class AccountService(
    private val repo: AccountRepository,
    private val idGenerator: IdGenerator,
) {
    val logger = KotlinLogging.logger {}

    fun createAccount(account: Account): Account {
        if (repo.existsByEmail(account.email)) {
            logger.warn { "Email ${account.email} already exists" }
            throw IllegalArgumentException("Email already exists")
        }
        val updatedAccount = account.copy(id = idGenerator.generateId())
        repo.save(updatedAccount)
        logger.info { "Account created: ${updatedAccount.id}" }
        return updatedAccount
    }

    fun getAccountById(id: String): Account? = repo.findById(id)

    fun getAllAccounts(): List<Account> = repo.findAll()

    fun updateAccount(
        id: String,
        account: Account,
    ): Account? {
        val existing = repo.findById(id) ?: return null
        if (existing.email != account.email && repo.existsByEmail(account.email)) {
            logger.warn { "Email ${account.email} already exists" }
            throw IllegalArgumentException("Email already exists")
        }
        val updated = account.copy(id = id)
        repo.save(updated)
        logger.info { "Account updated: $id" }
        return updated
    }

    fun deleteAccount(id: String): Boolean = repo.deleteById(id)
}
