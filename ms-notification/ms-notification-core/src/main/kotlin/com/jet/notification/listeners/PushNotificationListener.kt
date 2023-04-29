package com.jet.notification.listeners

import com.jet.notification.configs.KafkaConfig.Companion.PUSH_NOTIFICATION_CONTAINER_FACTORY_NAME
import com.jet.notification.dto.pushnotification.PushNotificationKafkaPayload
import com.jet.notification.services.pushnotification.PushNotificationService
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class PushNotificationListener(
    private val pushNotificationService: PushNotificationService,
) {

    @KafkaListener(
        topics = ["#{'\${settings.kafka.topics.push-notification}'}"],
        containerFactory = PUSH_NOTIFICATION_CONTAINER_FACTORY_NAME,
    )
    fun listen(payload: PushNotificationKafkaPayload) {
        if (payload.isEmpty()) {
            log.warn { "Push notification kafka payload is empty!" }
            return
        }

        payload.sendPushNotificationToTopicRequest?.let { request ->
            pushNotificationService.sendPushNotificationToTopic(request)
        }

        payload.sendPushNotificationToMobileDeviceRequest?.let { request ->
            pushNotificationService.sendPushNotificationToMobileDevice(request)
        }
    }
}
