package com.jet.objectstorage.controllers

import com.jet.objectstorage.api.FileApi
import com.jet.objectstorage.dto.FileDetailsDTO
import com.jet.objectstorage.services.FileService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
class FileController(
    private val fileService: FileService,
) : FileApi {

    override fun uploadFile(folder: String?, file: MultipartFile): FileDetailsDTO =
        fileService.uploadFile(folder = folder ?: DEFAULT_FOLDER_NAME, file = file)

    override fun getFileDetailsById(id: UUID): FileDetailsDTO =
        fileService.getFileDetailsById(id)

    override fun getFilesDetails(pageable: Pageable): Page<FileDetailsDTO> =
        fileService.getFilesDetails(pageable)

    override fun deleteFileById(id: UUID) {
        fileService.deleteFileById(id)
    }

    override fun deleteFilesByIds(ids: List<UUID>) {
        fileService.deleteFilesByIds(ids)
    }

    companion object {
        private const val DEFAULT_FOLDER_NAME = "files"
    }
}
