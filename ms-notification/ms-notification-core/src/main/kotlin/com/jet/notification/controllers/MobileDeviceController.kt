package com.jet.notification.controllers

import com.jet.notification.api.MobileDeviceApi
import com.jet.notification.dto.mobiledevice.RegisterOrUpdateMobileDeviceRequest
import com.jet.notification.services.MobileDeviceService
import org.springframework.web.bind.annotation.RestController

@RestController
class MobileDeviceController(
    private val mobileDeviceService: MobileDeviceService,
) : MobileDeviceApi {

    override fun registerOrUpdateMobileDevice(request: RegisterOrUpdateMobileDeviceRequest) {
        mobileDeviceService.registerOrUpdateMobileDevice(request)
    }

    override fun deleteMobileDeviceByDeviceId(deviceId: String) =
        mobileDeviceService.deleteMobileDeviceByDeviceId(deviceId)
}
