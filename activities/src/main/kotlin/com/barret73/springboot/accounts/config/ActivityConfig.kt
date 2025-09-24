package com.barret73.springboot.accounts.config

import com.barret73.springboot.accounts.domain.ActivityService
import com.barret73.springboot.client.AccountClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ActivityConfig {
    @Bean
    fun activityService(accountClient: AccountClient): ActivityService = ActivityService(accountClient)
}
