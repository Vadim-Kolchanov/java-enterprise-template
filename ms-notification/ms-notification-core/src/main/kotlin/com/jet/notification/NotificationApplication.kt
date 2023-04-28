package com.jet.notification

import com.jet.common.configs.feign.FeignClientGlobalConfig.Companion.FEIGN_CLIENT_GLOBAL_CONFIG_PACKAGE_NAME
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
        FEIGN_CLIENT_GLOBAL_CONFIG_PACKAGE_NAME,
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
