package com.jet.gatewaystarter.properties

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "settings.gateway-filters.basic-auth")
@ConditionalOnProperty(prefix = "settings.gateway-filters.basic-auth", name = ["username", "password"])
data class BasicAuthProperty(
    val username: String,
    val password: String,
) {
    fun toByteArray(): ByteArray = "$username:$password".toByteArray()
}
