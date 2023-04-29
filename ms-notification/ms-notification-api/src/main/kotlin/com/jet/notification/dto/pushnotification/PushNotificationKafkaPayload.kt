package com.jet.notification.dto.pushnotification

import io.swagger.v3.oas.annotations.media.Schema
import java.util.Objects.isNull

@Schema(description = "Push notification kafka payload")
data class PushNotificationKafkaPayload(
    @field:Schema(description = "Send push notification to mobile device")
    val sendPushNotificationToMobileDeviceRequest: SendPushNotificationToMobileDeviceRequest? = null,
    @field:Schema(description = "Send push notification to topic")
    val sendPushNotificationToTopicRequest: SendPushNotificationToTopicRequest? = null,
) {
    fun isEmpty(): Boolean = isNull(sendPushNotificationToMobileDeviceRequest)
            && isNull(sendPushNotificationToTopicRequest)
}
