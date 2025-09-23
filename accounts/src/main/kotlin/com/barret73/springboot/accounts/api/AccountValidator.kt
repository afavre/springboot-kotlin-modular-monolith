package com.barret73.springboot.accounts.api

import com.barret73.springboot.accounts.api.dto.AccountDto
import org.springframework.stereotype.Component

@Component
class AccountValidator {
    fun validate(account: AccountDto) {
        // Add complex validation logic here
    }
}
