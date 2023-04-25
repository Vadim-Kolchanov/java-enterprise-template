package com.jet.notification.exceptions

import com.jet.exceptions.BaseException
import com.jet.notification.domain.pushnotification.PushNotificationTopicCode
import org.springframework.http.HttpStatus

class PushNotificationTopicNotFoundException(
    code: PushNotificationTopicCode,
) : BaseException(
    errorMessage = "Push notification topic not found by code",
    httpStatus = HttpStatus.NOT_FOUND,
    message = "Push notification topic not found by code ($code)"
)