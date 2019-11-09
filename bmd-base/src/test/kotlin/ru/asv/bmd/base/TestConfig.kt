package ru.asv.bmd.base

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.asv.bmd.base.service.VoteService

@Configuration
open class TestConfig {

    @Bean
    open fun voteService() = VoteService()
}