package com.jet.gatewaystarter.utils

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

object FilterUtils {

    fun ServerHttpRequest.getAuthorizationHeaderValue(): String? =
        headers.getFirst(HttpHeaders.AUTHORIZATION)

    fun onError(exchange: ServerWebExchange, httpStatus: HttpStatus): Mono<Void> =
        exchange.response.apply {
            statusCode = httpStatus
        }.setComplete()
}
