package com.jet.notification

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import java.time.ZoneOffset
import java.util.Locale
import java.util.TimeZone
import javax.annotation.PostConstruct

@SpringBootApplication(
    scanBasePackages = [
        "com.jet.notification",
    ]
)
@ConfigurationPropertiesScan
class NotificationApplication {
    @PostConstruct
    fun init() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC))
        Locale.setDefault(Locale.ENGLISH)
    }
}

fun main(args: Array<String>) {
    runApplication<NotificationApplication>(*args)
}
