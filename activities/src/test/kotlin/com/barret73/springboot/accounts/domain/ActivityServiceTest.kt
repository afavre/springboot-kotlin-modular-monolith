package com.barret73.springboot.accounts.domain

import com.barret73.springboot.domain.Account
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class ActivityServiceTest :
    FunSpec({
        test(
            "Given a valid account, when createActivity is called, then it should create the activity",
        ) {
            // Mock the AccountClient
            val accountClient = mockk<com.barret73.springboot.client.AccountClient>()
            val activityService = ActivityService(accountClient)
            val account = Account(id = "1", firstName = "John", lastName = "Doe", email = "john.doe@example.com")
            every { accountClient.retrieveAccount("1") } returns account
            // Call the method under test
            val activity = activityService.createActivity("1", "Test Activity")
            // Verify the results
            activity.accountId shouldBe "1"
            activity.description shouldBe "Test Activity"
            verify(exactly = 1) { accountClient.retrieveAccount("1") }
        }
    })
