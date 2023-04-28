package com.jet.notification.controllers

import com.jet.notification.api.PushNotificationApi
import com.jet.notification.dto.pushnotification.PushNotificationTopicDTO
import com.jet.notification.dto.pushnotification.SendPushNotificationToMobileDeviceRequest
import com.jet.notification.dto.pushnotification.SendPushNotificationToTopicRequest
import com.jet.notification.services.pushnotification.PushNotificationService
import com.jet.notification.services.pushnotification.PushNotificationTopicService
import org.springframework.web.bind.annotation.RestController

@RestController
class PushNotificationController(
    private val pushNotificationTopicService: PushNotificationTopicService,
    private val pushNotificationService: PushNotificationService,
): PushNotificationApi {

    override fun getPushNotificationTopics(): List<PushNotificationTopicDTO> =
        pushNotificationTopicService.getPushNotificationTopics()

    override fun sendPushNotificationToMobileDevice(request: SendPushNotificationToMobileDeviceRequest) {
        pushNotificationService.sendPushNotificationToMobileDevice(request)
    }

    override fun sendPushNotificationToTopic(request: SendPushNotificationToTopicRequest) {
        pushNotificationService.sendPushNotificationToTopic(request)
    }
}