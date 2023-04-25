package com.jet.notification.exceptions

import com.jet.exceptions.BaseException
import org.springframework.http.HttpStatus

class MobileDeviceNotFoundByDeviceIdException(
    deviceId: String,
) : BaseException(
    errorMessage = "Mobile device not found by device id",
    httpStatus = HttpStatus.NOT_FOUND,
    message = "Mobile device not found by device id ($deviceId)"
)