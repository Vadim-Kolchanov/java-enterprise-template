package com.jet.notification.helpers.builders

import com.jet.notification.domain.mobiledevice.MobilePlatform
import com.jet.notification.dto.mobiledevice.RegisterOrUpdateMobileDeviceRequest
import com.jet.notification.entities.MobileDeviceEntity
import com.jet.notification.entities.pushnotification.PushNotificationTopicEntity
import com.jet.notification.helpers.constants.MobileDeviceConstants.DEVICE_ID
import com.jet.notification.helpers.constants.MobileDeviceConstants.FIREBASE_REGISTRATION_TOKEN
import com.jet.notification.helpers.constants.MobileDeviceConstants.MOBILE_PLATFORM
import java.time.OffsetDateTime

object MobileDeviceBuilder {

    fun registerOrUpdateMobileDeviceRequest(
        deviceId: String = DEVICE_ID,
        mobilePlatform: MobilePlatform = MOBILE_PLATFORM,
        firebaseRegistrationToken: String = FIREBASE_REGISTRATION_TOKEN,
    ) = RegisterOrUpdateMobileDeviceRequest(
        deviceId = deviceId,
        mobilePlatform = mobilePlatform,
        firebaseRegistrationToken = firebaseRegistrationToken,
    )

    fun mobileDeviceEntity(
        deviceId: String = DEVICE_ID,
        mobilePlatform: MobilePlatform = MOBILE_PLATFORM,
        firebaseRegistrationToken: String = FIREBASE_REGISTRATION_TOKEN,
        topics: Collection<PushNotificationTopicEntity> = emptySet(),
        createdAt: OffsetDateTime = OffsetDateTime.now(),
        updatedAt: OffsetDateTime = OffsetDateTime.now(),
        firebaseRegistrationTokenUpdatedAt: OffsetDateTime = OffsetDateTime.now(),
    ) = MobileDeviceEntity(
        deviceId = deviceId,
        mobilePlatform = mobilePlatform,
        firebaseRegistrationToken = firebaseRegistrationToken,
        topics = topics.toMutableSet(),
        createdAt = createdAt,
        updatedAt = updatedAt,
        firebaseRegistrationTokenUpdatedAt = firebaseRegistrationTokenUpdatedAt,
    )
}
