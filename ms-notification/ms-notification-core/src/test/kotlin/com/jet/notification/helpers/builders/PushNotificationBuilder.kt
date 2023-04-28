package com.jet.notification.helpers.builders

import com.jet.notification.domain.pushnotification.PushNotificationTopicCode
import com.jet.notification.dto.pushnotification.PushNotificationDTO
import com.jet.notification.dto.pushnotification.SendPushNotificationToMobileDeviceRequest
import com.jet.notification.dto.pushnotification.SendPushNotificationToTopicRequest
import com.jet.notification.helpers.constants.MobileDeviceConstants.DEVICE_ID
import com.jet.notification.helpers.constants.PushNotificationConstants.MESSAGE
import com.jet.notification.helpers.constants.PushNotificationConstants.PUSH_NOTIFICATION_TOPIC_CODE
import com.jet.notification.helpers.constants.PushNotificationConstants.TITLE

object PushNotificationBuilder {

    fun sendPushNotificationToTopicRequest(
        topicCode: PushNotificationTopicCode = PUSH_NOTIFICATION_TOPIC_CODE,
        title: String = TITLE,
        message: String = MESSAGE,
        params: Map<String, String> = emptyMap(),
    ) = SendPushNotificationToTopicRequest(
        topicCode = topicCode,
        pushNotification = PushNotificationDTO(
            title = title,
            message = message,
            params = params
        )
    )

    fun sendPushNotificationToMobileDeviceRequest(
        deviceId: String = DEVICE_ID,
        title: String = TITLE,
        message: String = MESSAGE,
        params: Map<String, String> = emptyMap(),
    ) = SendPushNotificationToMobileDeviceRequest(
        deviceId = deviceId,
        pushNotification = PushNotificationDTO(
            title = title,
            message = message,
            params = params
        )
    )
}
