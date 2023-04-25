package com.jet.notification.api

import com.jet.notification.dto.mobiledevice.RegisterOrUpdateMobileDeviceRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@Tag(name = "Mobile device")
interface MobileDeviceApi {

    @Operation(summary = "Register/Update mobile device")
    @PostMapping("/api/notification/mobile-devices")
    fun registerOrUpdateMobileDevice(@RequestBody @Valid request: RegisterOrUpdateMobileDeviceRequest)

    @Operation(summary = "Delete mobile device by identifier device")
    @DeleteMapping("/api/notification/mobile-devices/{device-id}")
    fun deleteMobileDeviceByDeviceId(
        @Parameter(description = "Identifier device")
        @PathVariable("device-id") deviceId: String
    )
}
