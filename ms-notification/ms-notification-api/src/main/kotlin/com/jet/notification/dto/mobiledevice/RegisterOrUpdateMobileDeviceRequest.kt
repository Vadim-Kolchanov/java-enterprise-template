package com.jet.notification.dto.mobiledevice

import com.jet.notification.domain.mobiledevice.MobilePlatform
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank

@Schema(description = "Request to register or update a mobile device")
data class RegisterOrUpdateMobileDeviceRequest(
    @field:Schema(description = "Identifier device")
    @field:NotBlank
    val deviceId: String,
    @field:Schema(description = "Mobile platform")
    val mobilePlatform: MobilePlatform,
    @field:Schema(description = "Registration token FCM")
    val firebaseRegistrationToken: String,
)
