package com.jet.objectstorage.exceptions

import com.jet.exceptionhandler.exceptions.BaseException
import org.springframework.http.HttpStatus

class FileContentTypeNotFoundException : BaseException(
    errorMessage = "File content type not found exception",
    httpStatus = HttpStatus.NOT_FOUND,
    message = "File content type not found exception"
)
