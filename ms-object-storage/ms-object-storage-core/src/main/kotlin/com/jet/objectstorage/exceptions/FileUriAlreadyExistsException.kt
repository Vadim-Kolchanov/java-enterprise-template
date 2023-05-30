package com.jet.objectstorage.exceptions

import com.jet.exceptionhandler.exceptions.BaseException
import org.springframework.http.HttpStatus

class FileUriAlreadyExistsException : BaseException(
    errorMessage = "File uri already exists",
    httpStatus = HttpStatus.BAD_REQUEST,
    message = "File uri already exists"
)
