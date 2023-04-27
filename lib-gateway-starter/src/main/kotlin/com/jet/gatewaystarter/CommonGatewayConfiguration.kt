package com.jet.gatewaystarter

import com.jet.gatewaystarter.configs.FeignDecoderConfig
import com.jet.gatewaystarter.filters.BasicAuthCheckGatewayFilterFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@ConditionalOnWebApplication
@Import(
    // filters
    BasicAuthCheckGatewayFilterFactory::class,

    // configs
    FeignDecoderConfig::class
)
@ConfigurationPropertiesScan
@Configuration(proxyBeanMethods = false)
class CommonGatewayConfiguration
