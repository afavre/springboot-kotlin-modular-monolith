package com.barret73.springboot.accounts.domain

import com.barret73.springboot.domain.Account

interface AccountRepository {
    fun save(account: Account)

    fun findById(id: String): Account?

    fun findAll(): List<Account>

    fun deleteById(id: String): Boolean

    fun existsByEmail(email: String): Boolean
}
