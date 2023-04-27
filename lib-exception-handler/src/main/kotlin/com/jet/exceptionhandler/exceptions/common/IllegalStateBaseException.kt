package com.jet.exceptionhandler.exceptions.common

import com.jet.exceptionhandler.exceptions.BaseException
import org.springframework.http.HttpStatus

class IllegalStateBaseException(
    message: String,
) : BaseException(
    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    errorMessage = "Invalid data state in the system",
    message = message,
)
