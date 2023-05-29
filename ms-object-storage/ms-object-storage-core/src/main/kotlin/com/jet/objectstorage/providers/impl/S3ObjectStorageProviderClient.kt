package com.jet.objectstorage.providers.impl

import com.jet.objectstorage.entities.FileEntity
import com.jet.objectstorage.properties.S3ClientProperties
import com.jet.objectstorage.providers.ObjectStorageProviderClient
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest

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
                .key(buildS3Key(file.folder, file.fileName))
                .contentType(file.contentType)
                .build(),
            RequestBody.fromBytes(fileBytes)
        )
    }

    private fun buildS3Key(folder: String, fileName: String): String = "$folder/$fileName"
}
