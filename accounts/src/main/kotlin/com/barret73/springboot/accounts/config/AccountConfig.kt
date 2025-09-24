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
    fun idGenerator(): IdGenerator = IdGenerator()

    @Bean
    fun accountRepository(): AccountRepository = InMemoryAccountRepository()

    @Bean
    fun accountService(
        accountRepository: AccountRepository,
        idGenerator: IdGenerator,
    ): AccountService = AccountService(accountRepository, idGenerator)

    @Bean
    fun accountClient(accountService: AccountService): AccountClient = AccountInternalClient(accountService)
}
