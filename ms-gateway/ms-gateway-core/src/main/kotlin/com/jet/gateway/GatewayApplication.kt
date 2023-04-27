package com.jet.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.ZoneOffset
import java.util.Locale
import java.util.TimeZone
import javax.annotation.PostConstruct

@SpringBootApplication
class GatewayApplication {
    @PostConstruct
    fun init() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC))
        Locale.setDefault(Locale.ENGLISH)
    }
}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}
