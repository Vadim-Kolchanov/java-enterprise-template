package com.jet.notification.dto.pushnotification

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request to send push notification to mobile device")
data class SendPushNotificationToMobileDeviceRequest(
    @field:Schema(description = "Identifier device")
    val deviceId: String,
    @field:Schema(description = "Push notification body")
    val pushNotification: PushNotificationDTO,
)
