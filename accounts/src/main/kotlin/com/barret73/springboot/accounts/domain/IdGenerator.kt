package com.barret73.springboot.accounts.domain

import java.util.UUID

class IdGenerator {
    fun generateId(): String = UUID.randomUUID().toString()
}
