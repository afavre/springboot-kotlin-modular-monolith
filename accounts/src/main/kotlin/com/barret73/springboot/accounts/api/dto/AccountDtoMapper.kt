package com.barret73.springboot.accounts.api.dto

import com.barret73.springboot.domain.Account

fun AccountDto.toDomain(): Account =
    Account(
        id = this.id,
        email = this.email!!,
        firstName = this.firstName,
        lastName = this.lastName,
    )

fun Account.toDto(): AccountDto =
    AccountDto(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
    )
