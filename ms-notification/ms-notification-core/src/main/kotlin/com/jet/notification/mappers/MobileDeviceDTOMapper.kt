package com.jet.notification.mappers

import com.jet.notification.dto.mobiledevice.RegisterOrUpdateMobileDeviceRequest
import com.jet.notification.entities.MobileDeviceEntity
import com.jet.notification.entities.pushnotification.PushNotificationTopicEntity

object MobileDeviceDTOMapper {

    fun RegisterOrUpdateMobileDeviceRequest.toMobileDeviceEntity(
        topics: List<PushNotificationTopicEntity>,
    ) = MobileDeviceEntity(
        deviceId = deviceId,
        mobilePlatform = mobilePlatform,
        firebaseRegistrationToken = firebaseRegistrationToken,
        topics = topics.toMutableSet(),
    )
}
