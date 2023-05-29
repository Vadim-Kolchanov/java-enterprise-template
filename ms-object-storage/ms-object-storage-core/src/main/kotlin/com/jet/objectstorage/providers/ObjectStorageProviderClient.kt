package com.jet.objectstorage.providers

import com.jet.objectstorage.entities.FileEntity
import org.springframework.web.util.UriComponents

interface ObjectStorageProviderClient {

    fun buildUri(folder: String, fileName: String): UriComponents

    fun putObject(file: FileEntity, fileBytes: ByteArray)
}