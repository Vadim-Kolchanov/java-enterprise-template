package com.jet.notification.dto.pushnotification

import com.jet.notification.domain.pushnotification.PushNotificationTopicCode
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request to send push notification to topic")
data class SendPushNotificationToTopicRequest(
    @field:Schema(description = "Push notification topic code")
    val topicCode: PushNotificationTopicCode,
    @field:Schema(description = "Push notification body")
    val pushNotification: PushNotificationDTO,
)
