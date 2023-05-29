package com.jet.objectstorage.providers.impl

import com.jet.objectstorage.entities.FileEntity
import com.jet.objectstorage.mappers.FileDTOMapper.toFileDetailsDTO
import com.jet.objectstorage.providers.ObjectStorageProviderClient
import mu.KotlinLogging
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder

private val log = KotlinLogging.logger {}

class LocalObjectStorageProviderClient : ObjectStorageProviderClient {

    override fun buildUri(folder: String, fileName: String): UriComponents = UriComponentsBuilder
        .newInstance()
        .build()

    override fun putObject(file: FileEntity, fileBytes: ByteArray) {
        log.info { "Local. Put object [${file.toFileDetailsDTO()}] to object storage" }
    }
}
