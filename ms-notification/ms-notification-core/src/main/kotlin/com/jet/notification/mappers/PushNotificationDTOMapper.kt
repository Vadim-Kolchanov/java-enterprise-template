package com.jet.notification.mappers

import com.jet.notification.dto.pushnotification.PushNotificationTopicDTO
import com.jet.notification.dto.pushnotification.SendPushNotificationToMobileDeviceRequest
import com.jet.notification.dto.pushnotification.SendPushNotificationToTopicRequest
import com.jet.notification.entities.MobileDeviceEntity
import com.jet.notification.entities.pushnotification.PushNotificationEntity
import com.jet.notification.entities.pushnotification.PushNotificationTopicEntity

object PushNotificationDTOMapper {

    fun PushNotificationTopicEntity.toPushNotificationTopicDTO() = PushNotificationTopicDTO(
        id = id,
        code = code,
        description = description,
    )

    fun SendPushNotificationToMobileDeviceRequest.toPushNotificationEntity(
        mobileDevice: MobileDeviceEntity,
    ) = PushNotificationEntity(
        firebaseRegistrationToken = mobileDevice.firebaseRegistrationToken,
        topicName = null,
        title = pushNotification.title,
        message = pushNotification.message,
    )

    fun SendPushNotificationToTopicRequest.toPushNotificationEntity(
        topic: PushNotificationTopicEntity,
    ) = PushNotificationEntity(
        firebaseRegistrationToken = null,
        topicName = topic.name,
        title = pushNotification.title,
        message = pushNotification.message,
    )
}
