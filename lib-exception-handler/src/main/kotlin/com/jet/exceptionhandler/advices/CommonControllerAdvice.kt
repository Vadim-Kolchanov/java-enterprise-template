package com.jet.exceptionhandler.advices

import com.jet.exceptionhandler.advices.AdviceOrders.COMMON_CONTROLLER_ADVICE
import com.jet.exceptionhandler.dto.HttpErrorDTO
import com.jet.exceptionhandler.dto.HttpErrorDTO.Companion.toHttpErrorDTO
import com.jet.exceptionhandler.exceptions.BaseException
import mu.KotlinLogging
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.validation.ConstraintViolationException

private val log = KotlinLogging.logger {}

@Order(COMMON_CONTROLLER_ADVICE)
@ControllerAdvice
class CommonControllerAdvice {

    @ExceptionHandler
    fun handleBaseException(ex: BaseException): ResponseEntity<HttpErrorDTO> {
        log.error(ex) {
            """
            Base exception with error code [${ex.errorCode}] occurred during an HTTP call.
            Message: ${ex.message}"
        """.trimIndent()
        }
        return ResponseEntity
            .status(ex.httpStatus)
            .contentType(APPLICATION_PROBLEM_JSON)
            .body(ex.toHttpErrorDTO())
    }

    @ExceptionHandler
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<HttpErrorDTO> {
        log.error(ex) { "Validation exception has occurred" }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(APPLICATION_PROBLEM_JSON)
            .body(ex.toHttpErrorDTO())
    }

    @ExceptionHandler
    fun handleBindException(ex: BindException): ResponseEntity<HttpErrorDTO> {
        log.error(ex) { "Validation exception has occurred" }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(APPLICATION_PROBLEM_JSON)
            .body(ex.toHttpErrorDTO())
    }

    @ExceptionHandler
    fun handleException(ex: Exception): ResponseEntity<HttpErrorDTO> {
        log.error(ex) { "An exception has been occurred" }
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(APPLICATION_PROBLEM_JSON)
            .body(ex.toHttpErrorDTO())
    }
}
