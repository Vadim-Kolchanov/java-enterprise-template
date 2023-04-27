package com.jet.exceptionhandler

import com.jet.exceptionhandler.advices.CommonControllerAdvice
import com.jet.exceptionhandler.decoders.CustomFeignErrorDecoder
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
@Import(CommonControllerAdvice::class, CustomFeignErrorDecoder::class)
class ExceptionHandlerConfiguration
