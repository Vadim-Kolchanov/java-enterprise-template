package com.jet.exceptionhandler.decoders

import com.fasterxml.jackson.databind.ObjectMapper
import com.jet.exceptionhandler.dto.HttpErrorDTO
import com.jet.exceptionhandler.exceptions.BaseException.Companion.toBaseException
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.stereotype.Component

@Component
class CustomFeignErrorDecoder(
    private val objectMapper: ObjectMapper
) : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception {
        return try {
            response.body().asInputStream().use {
                objectMapper.readValue(it, HttpErrorDTO::class.java)
                    .toBaseException("Feign client error with URL [${response.request().url()}]")
            }
        } catch (ex: Exception) {
            val errorDecoder = ErrorDecoder.Default()
            errorDecoder.decode(methodKey, response)
        }
    }
}
