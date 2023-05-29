package com.jet.objectstorage.mappers

import com.jet.objectstorage.dto.FileDetailsDTO
import com.jet.objectstorage.entities.FileEntity
import com.jet.objectstorage.exceptions.FileContentTypeNotFoundException
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponents

object FileDTOMapper {

    fun MultipartFile.toFileEntity(folder: String, uri: UriComponents) = FileEntity(
        folder = folder,
        fileName = name,
        uri = uri.toUriString(),
        contentType = contentType ?: throw FileContentTypeNotFoundException(),
        sizeInBytes = size,
    )

    fun FileEntity.toFileDetailsDTO() = FileDetailsDTO(
        id = id,
        fileName = fileName,
        uri = uri,
        contentType = contentType,
        sizeInBytes = sizeInBytes,
        dateCreated = createdAt,
    )
}
