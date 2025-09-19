package com.barret73.springboot.domain.account

import com.barret73.springboot.domain.account.model.Account

interface AccountRepository {
    fun save(account: Account)

    fun findById(id: String): Account?

    fun findAll(): List<Account>

    fun deleteById(id: String): Boolean

    fun existsByEmail(email: String): Boolean
}
