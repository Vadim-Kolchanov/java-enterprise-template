package com.jet.gatewaystarter.filters

import com.jet.gatewaystarter.utils.FilterUtils
import com.jet.gatewaystarter.filters.FilterOrders.BASIC_AUTH_CHECK_FILTER_ORDER
import com.jet.gatewaystarter.properties.BasicAuthProperty
import com.jet.gatewaystarter.utils.BasicAuthUtils.extractBasicAuth
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(prefix = "settings.gateway-filters.basic-auth", name = ["username", "password"])
class BasicAuthCheckGatewayFilterFactory(
    private val basicAuthProperty: BasicAuthProperty
) : AbstractGatewayFilterFactory<Any>(), Ordered {

    override fun apply(config: Any): GatewayFilter = GatewayFilter { exchange, chain ->
        val basicAuth: ByteArray = exchange.extractBasicAuth()
            ?: return@GatewayFilter FilterUtils.onError(exchange, HttpStatus.UNAUTHORIZED)

        if (!basicAuth.contentEquals(basicAuthProperty.toByteArray()))
            return@GatewayFilter FilterUtils.onError(exchange, HttpStatus.UNAUTHORIZED)

        return@GatewayFilter chain.filter(exchange)
    }

    override fun getOrder(): Int = BASIC_AUTH_CHECK_FILTER_ORDER
}
