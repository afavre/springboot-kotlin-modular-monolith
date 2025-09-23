package com.barret73.springboot.accounts.domain

import com.barret73.springboot.client.AccountClient
import com.barret73.springboot.domain.Activity
import java.util.*

class ActivityService(private val accountClient: AccountClient) {

    // Example method that uses AccountClient with no direct link to Account module
    fun createActivity(accountId: String, description: String): Activity {
        // ...
        val account = accountClient.retrieveAccount(accountId)
            ?: throw IllegalArgumentException("Account with ID $accountId does not exist.")
        // ...
        return Activity(id = UUID.randomUUID().toString(), accountId = account.id!!, description = description)
    }

}
