package com.jet.notification.helpers.builders

import com.jet.notification.domain.mobiledevice.MobilePlatform
import com.jet.notification.dto.mobiledevice.RegisterOrUpdateMobileDeviceRequest
import com.jet.notification.helpers.constants.MobileDeviceConstant.DEVICE_ID
import com.jet.notification.helpers.constants.MobileDeviceConstant.FIREBASE_REGISTRATION_TOKEN
import com.jet.notification.helpers.constants.MobileDeviceConstant.MOBILE_PLATFORM

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
}
