package com.up42.interstellar

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MvcConfig {

    @Bean
    fun kotlinModule() = KotlinModule()
}
