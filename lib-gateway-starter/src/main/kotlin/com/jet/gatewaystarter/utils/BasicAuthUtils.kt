package com.jet.gatewaystarter.utils

import com.jet.gatewaystarter.utils.FilterUtils.getAuthorizationHeaderValue
import org.springframework.web.server.ServerWebExchange
import java.util.Base64

object BasicAuthUtils {

    fun ServerWebExchange.extractBasicAuth(): ByteArray? = request.getAuthorizationHeaderValue()
        ?.let { decodeBase64FromHeader(it) }

    private fun decodeBase64FromHeader(authorizationHeaderValue: String): ByteArray = Base64.getDecoder()
        .decode(authorizationHeaderValue.removePrefix("Basic "))
}
