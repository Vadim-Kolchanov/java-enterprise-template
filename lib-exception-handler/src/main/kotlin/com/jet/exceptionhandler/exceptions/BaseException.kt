package com.jet.exceptionhandler.exceptions

import com.jet.exceptionhandler.dto.HttpErrorDTO
import org.springframework.http.HttpStatus

open class BaseException(
    val httpStatus: HttpStatus,
    val errorCode: String,
    val errorMessage: String,
    val data: Any? = null,
    message: String? = null,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {

    constructor(
        httpStatus: HttpStatus,
        errorMessage: String,
        data: Any? = null,
        message: String? = null,
        cause: Throwable? = null,
    ) : this(
        httpStatus = httpStatus,
        errorCode = httpStatus.value().toString(),
        errorMessage = errorMessage,
        data = data,
        message = message,
        cause = cause
    )

    companion object {
        fun HttpErrorDTO.toBaseException(message: String? = null): BaseException =
            BaseException(
                httpStatus = HttpStatus.valueOf(status),
                errorCode = code,
                errorMessage = error,
                data = data,
                message = message,
            )
    }
}
