package com.jet.exceptions.common

import com.jet.exceptions.BaseException
import org.springframework.http.HttpStatus

class IllegalStateBaseException(
    message: String,
): BaseException(
    errorMessage = "Invalid data state in the system",
    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    message = message,
)
