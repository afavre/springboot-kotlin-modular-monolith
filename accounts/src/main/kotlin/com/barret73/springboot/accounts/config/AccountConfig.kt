package com.barret73.springboot.accounts.config

import com.barret73.springboot.accounts.api.AccountInternalClient
import com.barret73.springboot.accounts.domain.AccountRepository
import com.barret73.springboot.accounts.domain.AccountService
import com.barret73.springboot.accounts.domain.IdGenerator
import com.barret73.springboot.accounts.infrastructure.InMemoryAccountRepository
import com.barret73.springboot.client.AccountClient
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

    @Bean
    fun accountClient(accountService: AccountService): AccountClient {
        return AccountInternalClient(accountService)
    }
    
}
