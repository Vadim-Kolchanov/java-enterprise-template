package com.jet.common.configs.feign

import feign.Logger
import feign.Logger.Level.FULL
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.jet"])
class FeignClientGlobalConfig {

    @Bean
    fun feignClientLoggingLevel(): Logger.Level = FULL

    companion object {
        const val FEIGN_CLIENT_GLOBAL_CONFIG_PACKAGE_NAME = "com.jet.common.configs.feign"
    }
}
