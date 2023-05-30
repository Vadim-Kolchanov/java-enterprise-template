package com.jet.objectstorage.exceptions

import com.jet.exceptionhandler.exceptions.BaseException
import org.springframework.http.HttpStatus
import java.util.UUID

class FileNotFoundException(
    id: UUID,
) : BaseException(
    errorMessage = "File not found",
    httpStatus = HttpStatus.NOT_FOUND,
    message = "File not found by id ($id)"
)
