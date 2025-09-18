package com.barret73.springboot.api.account.dto

import com.barret73.springboot.domain.account.model.Account

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
