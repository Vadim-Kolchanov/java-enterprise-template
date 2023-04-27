package com.jet.notification.exceptions

import com.google.firebase.messaging.FirebaseMessagingException
import com.jet.exceptionhandler.exceptions.BaseException
import org.springframework.http.HttpStatus

class InvalidFirebaseRegistrationTokenException(
    firebaseRegistrationToken: String,
    cause: FirebaseMessagingException
) : BaseException(
    errorMessage = "FCM registration token is not a valid",
    httpStatus = HttpStatus.BAD_REQUEST,
    message = "FCM registration token [$firebaseRegistrationToken] is not a valid",
    cause = cause,
)
