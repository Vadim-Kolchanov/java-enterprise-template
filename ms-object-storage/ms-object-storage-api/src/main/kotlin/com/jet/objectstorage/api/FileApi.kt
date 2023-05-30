package com.jet.objectstorage.api

import com.jet.objectstorage.dto.FileDetailsDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Tag(name = "File")
interface FileApi {

    @Operation(summary = "Upload file")
    @PostMapping("/api/object-storage/files", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(
        @Parameter(description = "Folder name")
        @RequestPart("folder") folder: String? = null,
        @Parameter(description = "File")
        @RequestPart("file") file: MultipartFile,
    ): FileDetailsDTO

    @Operation(summary = "Get file details by id")
    @GetMapping("/api/object-storage/files/{id}")
    fun getFileDetailsById(
        @Parameter(description = "File identifier")
        @PathVariable id: UUID
    ): FileDetailsDTO

    @Operation(summary = "Get files details page")
    @GetMapping("/api/object-storage/files")
    fun getFilesDetails(
        @PageableDefault(size = 10)
        @ParameterObject pageable: Pageable
    ): Page<FileDetailsDTO>

    @Operation(summary = "Delete file")
    @DeleteMapping("/api/object-storage/files/{id}")
    fun deleteFileById(
        @Parameter(description = "File identifier")
        @PathVariable id: UUID
    )

    @Operation(summary = "Delete files")
    @DeleteMapping("/api/object-storage/files")
    fun deleteFilesByIds(
        @Parameter(description = "File identifiers")
        @RequestBody ids: List<UUID>
    )
}
