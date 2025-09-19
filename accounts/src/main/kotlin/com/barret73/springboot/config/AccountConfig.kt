package com.barret73.springboot.config

import com.barret73.springboot.domain.account.AccountRepository
import com.barret73.springboot.domain.account.AccountService
import com.barret73.springboot.domain.account.IdGenerator
import com.barret73.springboot.infrastructure.account.InMemoryAccountRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AccountConfig {

    @Bean
    fun idGenerator(): IdGenerator {
        return IdGenerator()
    }

    @Bean
    fun accountRepository(): AccountRepository {
        return InMemoryAccountRepository()
    }

    @Bean
    fun accountService(
        accountRepository: AccountRepository,
        idGenerator: IdGenerator,
    ): AccountService {
        return AccountService(accountRepository, idGenerator)
    }
}
