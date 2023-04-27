package com.jet.exceptionhandler.dto

import com.jet.exceptionhandler.exceptions.BaseException
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import java.time.OffsetDateTime
import javax.validation.ConstraintViolationException

/**
 * DTO schema for providing an HTTP error to the client side
 */
data class HttpErrorDTO(
    /** HTTP status [org.springframework.http.HttpStatus] */
    val status: Int,
    /** System error code */
    val code: String,
    /** Error message */
    val error: String,
    /** Data structure for more information */
    val data: Any? = null,
    /** Error time */
    val timestamp: OffsetDateTime = OffsetDateTime.now()
) {
    companion object {
        fun BaseException.toHttpErrorDTO(): HttpErrorDTO = HttpErrorDTO(
            status = httpStatus.value(),
            code = errorCode,
            error = errorMessage,
            data = data,
        )

        fun BindException.toHttpErrorDTO(): HttpErrorDTO = HttpErrorDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            code = HttpStatus.BAD_REQUEST.value().toString(),
            error = "Some request attributes were not validated",
            data = fieldErrors.map { "Field [${it.field}]: ${it.defaultMessage}" },
        )

        fun ConstraintViolationException.toHttpErrorDTO(): HttpErrorDTO = HttpErrorDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            code = HttpStatus.BAD_REQUEST.value().toString(),
            error = "Some request attributes were not validated",
            data = mapOf(
                "message" to message
            ),
        )

        fun Exception.toHttpErrorDTO(): HttpErrorDTO = HttpErrorDTO(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            code = HttpStatus.INTERNAL_SERVER_ERROR.value().toString(),
            error = "Internal server error",
        )
    }
}
