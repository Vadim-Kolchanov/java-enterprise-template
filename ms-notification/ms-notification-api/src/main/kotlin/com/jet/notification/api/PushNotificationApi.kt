package com.jet.notification.api

import com.jet.notification.dto.pushnotification.PushNotificationTopicDTO
import com.jet.notification.dto.pushnotification.SendPushNotificationToMobileDeviceRequest
import com.jet.notification.dto.pushnotification.SendPushNotificationToTopicRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@Tag(name = "Push notification")
interface PushNotificationApi {

    @Operation(summary = "Get push notification topics")
    @GetMapping("/api/notification/push/topics")
    fun getPushNotificationTopics(): List<PushNotificationTopicDTO>

    @Operation(summary = "Send push notification to mobile device")
    @PostMapping("/api/notification/push/send-to-mobile-device")
    fun sendPushNotificationToMobileDevice(@Valid @RequestBody request: SendPushNotificationToMobileDeviceRequest)

    @Operation(summary = "Send push notification to topic")
    @PostMapping("/api/notification/push/send-to-topic")
    fun sendPushNotificationToTopic(@Valid @RequestBody request: SendPushNotificationToTopicRequest)
}
