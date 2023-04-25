package com.jet.notification.providers

import com.jet.notification.entities.MobileDeviceEntity
import com.jet.notification.entities.pushnotification.PushNotificationEntity

interface PushNotificationProviderClient {

    fun subscribeToTopics(mobileDevice: MobileDeviceEntity)

    fun unsubscribeFromTopics(mobileDevice: MobileDeviceEntity)

    fun sendPushNotificationToTopic(pushNotification: PushNotificationEntity, params: Map<String, String>)

    fun sendPushNotificationToToken(pushNotification: PushNotificationEntity, params: Map<String, String>)
}
