package com.jet.objectstorage.configs

import com.jet.objectstorage.properties.S3ClientProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI

@Configuration
@ConditionalOnProperty(prefix = "settings.object-storage", name = ["provider"], havingValue = "s3")
class S3ClientConfig(
    private val s3ClientProperties: S3ClientProperties,
) {

    @Bean
    fun s3Client(): S3Client = S3Client.builder()
        .credentialsProvider(staticCredentialsProvider())
        .endpointOverride(URI(s3ClientProperties.url))
        .region(Region.of(s3ClientProperties.region))
        .build()

    private fun staticCredentialsProvider(): StaticCredentialsProvider = StaticCredentialsProvider.create(
        AwsBasicCredentials.create(
            s3ClientProperties.accessKeyId,
            s3ClientProperties.secretAccessKey
        )
    )
}
