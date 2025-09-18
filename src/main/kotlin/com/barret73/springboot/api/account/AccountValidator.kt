package com.barret73.springboot.api.account

import com.barret73.springboot.api.account.dto.AccountDto
import org.springframework.stereotype.Component

@Component
class AccountValidator {
    fun validate(account: AccountDto) {
        // Add complex validation logic here
    }
}
