package com.barret73.springboot.domain.account

class IdGenerator {
    fun generateId(): String = java.util.UUID.randomUUID().toString()
}
