package com.jet.notification.providers.impl

import com.jet.notification.entities.MobileDeviceEntity
import com.jet.notification.entities.pushnotification.PushNotificationEntity
import com.jet.notification.providers.PushNotificationProviderClient
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class LocalPushNotificationProviderClient : PushNotificationProviderClient {

    override fun subscribeToTopics(mobileDevice: MobileDeviceEntity) {
        log.info { "Local. Subscribing device with id [${mobileDevice.deviceId}] and token [${mobileDevice.firebaseRegistrationToken}] to topics ${mobileDevice.topics.map { it.name }}..." }
    }

    override fun unsubscribeFromTopics(mobileDevice: MobileDeviceEntity) {
        log.info { "Local. Unsubscribing device with id [${mobileDevice.deviceId}] and token [${mobileDevice.firebaseRegistrationToken}] from topics ${mobileDevice.topics.map { it.name }}..." }
    }

    override fun sendPushNotificationToTopic(pushNotification: PushNotificationEntity, params: Map<String, String>) {
        log.info {
            """
            Local. Sending push notification with id [${pushNotification.id}] to topic [${pushNotification.topicName}]
            with [title: '${pushNotification.title}', message: '${pushNotification.message}', params: $params]
            """
        }
    }

    override fun sendPushNotificationToToken(pushNotification: PushNotificationEntity, params: Map<String, String>) {
        log.info {
            """
            Local. Sending push notification with id [${pushNotification.id}] to token [${pushNotification.firebaseRegistrationToken}]
            with [title: '${pushNotification.title}', message: '${pushNotification.message}', params: $params]
            """
        }
    }
}