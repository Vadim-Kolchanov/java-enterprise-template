package com.jet.test.extensions

import com.jet.test.extensions.MinioContainerExtension.MinioEnvironment.MINIO_COMMAND
import com.jet.test.extensions.MinioContainerExtension.MinioEnvironment.MINIO_IMAGE
import com.jet.test.extensions.MinioContainerExtension.MinioEnvironment.MINIO_PORT
import com.jet.test.extensions.MinioContainerExtension.MinioEnvironment.MINIO_ROOT_PASSWORD
import com.jet.test.extensions.MinioContainerExtension.MinioEnvironment.MINIO_ROOT_USER
import com.jet.test.extensions.MinioContainerExtension.MinioEnvironment.MINIO_SITE_REGION
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

open class MinioContainerExtension : BeforeAllCallback {

    private enum class MinioEnvironment(
        default: String
    ) {
        MINIO_IMAGE("minio/minio"),
        MINIO_COMMAND("minio server /data"),
        MINIO_ROOT_USER("testuser"),
        MINIO_ROOT_PASSWORD("testpassword"),
        MINIO_SITE_REGION("eu-west-1"),
        MINIO_PORT("9000");

        val value: String = System.getenv("TEST_$name") ?: default
    }

    override fun beforeAll(extensionContext: ExtensionContext) {
        minioContainer.start()

        System.setProperty(
            "settings.object-storage.s3-client.url",
            "http://localhost:${minioContainer.firstMappedPort}"
        )
        System.setProperty("settings.object-storage.s3-client.access-key-id", MINIO_ROOT_USER.value)
        System.setProperty("settings.object-storage.s3-client.secret-access-key", MINIO_ROOT_PASSWORD.value)
        System.setProperty("settings.object-storage.s3-client.region", MINIO_SITE_REGION.value)
    }

    companion object {
        private val minioContainer =
            GenericContainer(DockerImageName.parse(MINIO_IMAGE.value))
                .withEnv(MINIO_ROOT_USER.name, MINIO_ROOT_USER.value)
                .withEnv(MINIO_ROOT_PASSWORD.name, MINIO_ROOT_PASSWORD.value)
                .withEnv(MINIO_SITE_REGION.name, MINIO_SITE_REGION.value)
                .withExposedPorts(MINIO_PORT.value.toInt())
                .withCommand(MINIO_COMMAND.value)
    }
}
