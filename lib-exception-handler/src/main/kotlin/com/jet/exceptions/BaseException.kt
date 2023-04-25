package com.jet.exceptions

import org.springframework.http.HttpStatus

open class BaseException(
    val errorMessage: String,
    val code: String,
    val data: Any? = null,
    message: String? = null,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {

    constructor(
        errorMessage: String,
        httpStatus: HttpStatus,
        data: Any? = null,
        message: String? = null,
        cause: Throwable? = null,
    ) : this(
        errorMessage = errorMessage,
        code = httpStatus.value().toString(),
        data = data,
        message = message,
        cause = cause
    )
}
