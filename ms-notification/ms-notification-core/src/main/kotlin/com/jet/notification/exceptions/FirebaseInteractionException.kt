package com.jet.notification.exceptions

import com.jet.exceptions.BaseException
import org.springframework.http.HttpStatus

class FirebaseInteractionException(
    message: String,
    cause: Throwable,
) : BaseException(
    errorMessage = "Firebase interaction error",
    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    message = message,
    cause = cause,
)
