package com.jet.gatewaystarter.configs

import feign.form.spring.SpringFormEncoder
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignDecoderConfig {

    private val messageConverters = ObjectFactory<HttpMessageConverters> { HttpMessageConverters() }

    @Bean
    fun feignDecoder(): SpringDecoder = SpringDecoder(messageConverters)

    /**
     * SpringEncoder does not know how to process a request to transfer files between microservices using feign client.
     * So the SpringFormEncoder wrapper is used
     */
    @Bean
    fun feignFormEncoder(): SpringFormEncoder = SpringFormEncoder(SpringEncoder(messageConverters))
}
