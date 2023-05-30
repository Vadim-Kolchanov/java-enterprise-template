package com.jet.objectstorage.providers.impl

import com.jet.objectstorage.entities.FileEntity
import com.jet.objectstorage.properties.S3ClientProperties
import com.jet.objectstorage.providers.ObjectStorageProviderClient
import mu.KotlinLogging
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse
import software.amazon.awssdk.services.s3.model.ObjectIdentifier
import software.amazon.awssdk.services.s3.model.PutObjectRequest

private val log = KotlinLogging.logger {}

class S3ObjectStorageProviderClient(
    private val s3Client: S3Client,
    private val s3ClientProperties: S3ClientProperties,
) : ObjectStorageProviderClient {

    override fun buildUri(folder: String, fileName: String): UriComponents = UriComponentsBuilder
        .fromHttpUrl(s3ClientProperties.url)
        .pathSegment(s3ClientProperties.bucket, buildS3Key(folder, fileName))
        .build()

    override fun putObject(file: FileEntity, fileBytes: ByteArray) {
        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(s3ClientProperties.bucket)
                .key(file.toS3Key())
                .contentType(file.contentType)
                .build(),
            RequestBody.fromBytes(fileBytes)
        )
    }

    override fun deleteObject(file: FileEntity) {
        s3Client.deleteObject(
            DeleteObjectRequest.builder()
                .bucket(s3ClientProperties.bucket)
                .key(file.toS3Key())
                .build()
        )
    }

    override fun deleteObjects(files: Collection<FileEntity>): Collection<FileEntity> {
        if (files.isEmpty()) return files

        val objectIdentifiers: List<ObjectIdentifier> = files.map { file ->
            ObjectIdentifier.builder()
                .key(file.toS3Key())
                .build()
        }

        s3Client.deleteObjects { deletionBuilder ->
            deletionBuilder.bucket(s3ClientProperties.bucket)
            deletionBuilder.delete { it.objects(objectIdentifiers) }
        }.let { response ->
            if (response.errors().isNotEmpty()) {
                log.error { "Error deleting objects [${response.errors().size}] from S3 storage: ${response.toBeautify()}" }
                return files.filter { file -> response.errors().all { file.toS3Key() != it.key() } }
            }
        }

        return files
    }

    private fun buildS3Key(folder: String, fileName: String): String = "$folder/$fileName"

    private fun DeleteObjectsResponse.toBeautify(): String =
        errors().joinToString(",\n", "\n") { "${it.key()}: ${it.message()}" }

    private fun FileEntity.toS3Key(): String = buildS3Key(folder, fileName)
}
