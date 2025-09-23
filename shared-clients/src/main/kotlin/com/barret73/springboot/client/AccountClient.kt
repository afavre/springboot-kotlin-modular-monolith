package com.barret73.springboot.client

import com.barret73.springboot.domain.Account

interface AccountClient {
    fun retrieveAccount(accountId: String): Account?
}
