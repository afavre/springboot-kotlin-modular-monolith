package com.barret73.springboot.api.account.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AccountDto(
    val id: String? = null,
    @field:NotBlank(message = "Email must not be blank")
    @field:Email(message = "Email should be valid")
    val email: String? = null,
    @field:Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    val firstName: String? = null,
    @field:Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters")
    val lastName: String? = null,
)
