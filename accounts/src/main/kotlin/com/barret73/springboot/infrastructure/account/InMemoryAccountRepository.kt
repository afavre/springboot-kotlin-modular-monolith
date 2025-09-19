package com.barret73.springboot.infrastructure.account

import com.barret73.springboot.domain.account.AccountRepository
import com.barret73.springboot.domain.account.model.Account
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryAccountRepository : AccountRepository {
    private val accounts = ConcurrentHashMap<String, Account>()

    override fun save(account: Account) {
        accounts[account.id!!] = account
    }

    override fun findById(id: String): Account? = accounts[id]

    override fun findAll(): List<Account> = accounts.values.toList()

    override fun deleteById(id: String): Boolean = accounts.remove(id) != null

    override fun existsByEmail(email: String): Boolean = accounts.values.any { it.email == email }
}
