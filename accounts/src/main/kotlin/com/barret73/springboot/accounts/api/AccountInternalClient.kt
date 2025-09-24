package com.barret73.springboot.accounts.api

import com.barret73.springboot.accounts.domain.AccountService
import com.barret73.springboot.client.AccountClient
import com.barret73.springboot.domain.Account

class AccountInternalClient(
    private val accountService: AccountService,
) : AccountClient {
    override fun retrieveAccount(accountId: String): Account? = accountService.getAccountById(accountId)
}
