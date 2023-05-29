package com.jet.objectstorage.dto

import java.time.OffsetDateTime
import java.util.UUID

data class FileDetailsDTO(
    val id: UUID,
    val fileName: String,
    val uri: String,
    val contentType: String,
    val sizeInBytes: Long,
    val dateCreated: OffsetDateTime,
)
