package com.jet.objectstorage.services

import com.jet.objectstorage.dto.FileDetailsDTO
import com.jet.objectstorage.mappers.FileDTOMapper.toFileDetailsDTO
import com.jet.objectstorage.mappers.FileDTOMapper.toFileEntity
import com.jet.objectstorage.providers.ObjectStorageProviderClient
import com.jet.objectstorage.repositories.FileRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService(
    private val fileRepository: FileRepository,
    private val objectStorageProviderClient: ObjectStorageProviderClient,
) {

    fun uploadFile(folder: String, file: MultipartFile): FileDetailsDTO = file
        .toFileEntity(folder = folder, uri = objectStorageProviderClient.buildUri(folder, file.name))
        .also { objectStorageProviderClient.putObject(it, file.bytes) }
        .let(fileRepository::save)
        .toFileDetailsDTO()
}
