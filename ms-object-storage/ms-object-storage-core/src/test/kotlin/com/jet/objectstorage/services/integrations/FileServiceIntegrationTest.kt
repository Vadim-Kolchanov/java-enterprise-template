package com.jet.objectstorage.services.integrations

import com.jet.objectstorage.dto.FileDetailsDTO
import com.jet.objectstorage.services.FileService
import com.jet.objectstorage.services.integrations.FileServiceIntegrationTest.Companion.MINIO_BUCKET
import com.jet.test.annotations.WithMinio
import com.jet.test.annotations.WithPostgres
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.CreateBucketRequest
import java.util.function.Consumer

@WithPostgres
@WithMinio
@SpringBootTest(
    properties = [
        "settings.object-storage.provider=s3",
        "settings.object-storage.s3-client.bucket=$MINIO_BUCKET"
    ]
)
class FileServiceIntegrationTest {

    @Autowired
    lateinit var fileService: FileService

    @Nested
    open inner class UploadFile {

        @Test
        @Transactional
        @Rollback(true)
        open fun `Should successfully upload file to object storage`() {
            val mockFile: MultipartFile = MockMultipartFile(
                FILE_NAME,
                ORIGINAL_FILE_NAME,
                CONTENT_TYPE,
                CONTENT.encodeToByteArray()
            )

            val fileDetails: FileDetailsDTO = fileService.uploadFile(FOLDER, mockFile)

            assertThat(fileDetails).satisfies(Consumer {
                assertThat(it.fileName).isEqualTo(ORIGINAL_FILE_NAME)
                assertThat(it.contentType).isEqualTo(CONTENT_TYPE)
                assertThat(it.sizeInBytes).isEqualTo(CONTENT.encodeToByteArray().size.toLong())
            })
        }
    }

    companion object {
        const val MINIO_BUCKET = "test"

        private const val FILE_NAME = "file"
        private const val ORIGINAL_FILE_NAME = "test.txt"
        private const val CONTENT_TYPE = MediaType.TEXT_PLAIN_VALUE
        private const val CONTENT = "Test"

        private const val FOLDER = "TestFolder"

        @JvmStatic
        @BeforeAll
        fun createBucket(@Autowired s3Client: S3Client) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(MINIO_BUCKET).build())
        }
    }
}
