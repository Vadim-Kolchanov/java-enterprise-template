package com.jet.objectstorage.services

import com.jet.objectstorage.dto.FileDetailsDTO
import com.jet.objectstorage.entities.FileEntity
import com.jet.objectstorage.exceptions.FileNotFoundException
import com.jet.objectstorage.exceptions.FileUriAlreadyExistsException
import com.jet.objectstorage.mappers.FileDTOMapper.toFileDetailsDTO
import com.jet.objectstorage.mappers.FileDTOMapper.toFileEntity
import com.jet.objectstorage.providers.ObjectStorageProviderClient
import com.jet.objectstorage.repositories.FileRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class FileService(
    private val fileRepository: FileRepository,
    private val objectStorageProviderClient: ObjectStorageProviderClient,
) {

    @Transactional(readOnly = true)
    fun getFileById(id: UUID): FileEntity = fileRepository.findByIdOrNull(id)
        ?: throw FileNotFoundException(id)

    @Transactional
    fun uploadFile(folder: String, file: MultipartFile): FileDetailsDTO {
        val fileName: String = file.originalFilename ?: UUID.randomUUID().toString()

        val fileEntity: FileEntity = file.toFileEntity(
            folder = folder,
            fileName = fileName,
            uri = objectStorageProviderClient.buildUri(folder, fileName)
        )

        if (fileRepository.existsByUri(fileEntity.uri)) throw FileUriAlreadyExistsException()

        return fileEntity
            .also { objectStorageProviderClient.putObject(it, file.bytes) }
            .let(fileRepository::save)
            .toFileDetailsDTO()
    }

    @Transactional(readOnly = true)
    fun getFileDetailsById(id: UUID): FileDetailsDTO = getFileById(id).toFileDetailsDTO()

    @Transactional(readOnly = true)
    fun getFilesDetails(pageable: Pageable): Page<FileDetailsDTO> = fileRepository.findAll(pageable)
        .map { it.toFileDetailsDTO() }

    @Transactional
    fun deleteFileById(id: UUID): Unit = getFileById(id)
        .also(objectStorageProviderClient::deleteObject)
        .let(fileRepository::delete)

    @Transactional
    fun deleteFilesByIds(ids: Collection<UUID>): Unit = fileRepository.findAllById(ids)
        .let(objectStorageProviderClient::deleteObjects)
        .let(fileRepository::deleteAll)
}
