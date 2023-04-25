package com.jet.notification.configs.pushnotification

import com.google.firebase.messaging.FirebaseMessaging
import com.jet.notification.providers.PushNotificationProviderClient
import com.jet.notification.providers.impl.FirebasePushNotificationProviderClient
import com.jet.notification.providers.impl.LocalPushNotificationProviderClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PushNotificationProviderConfig {

    @Bean
    @ConditionalOnProperty(prefix = PREFIX, name = [PROPERTY_NAME_PROVIDER], havingValue = "local")
    fun localPushNotificationProviderClient(): PushNotificationProviderClient =
        LocalPushNotificationProviderClient()

    @Bean
    @ConditionalOnProperty(prefix = PREFIX, name = [PROPERTY_NAME_PROVIDER], havingValue = "firebase")
    fun firebasePushNotificationProviderClient(firebaseMessaging: FirebaseMessaging): PushNotificationProviderClient =
        FirebasePushNotificationProviderClient(firebaseMessaging)

    companion object {
        private const val PREFIX = "settings.push-notification"
        private const val PROPERTY_NAME_PROVIDER = "provider"
    }
}
