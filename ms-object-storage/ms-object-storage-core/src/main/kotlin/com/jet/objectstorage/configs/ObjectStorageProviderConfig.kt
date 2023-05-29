package com.jet.objectstorage.configs

import com.jet.objectstorage.properties.S3ClientProperties
import com.jet.objectstorage.providers.ObjectStorageProviderClient
import com.jet.objectstorage.providers.impl.LocalObjectStorageProviderClient
import com.jet.objectstorage.providers.impl.S3ObjectStorageProviderClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class ObjectStorageProviderConfig {

    @Bean
    @ConditionalOnProperty(prefix = PREFIX, name = [PROPERTY_NAME_PROVIDER], havingValue = "local")
    fun localObjectStorageProviderClient(): ObjectStorageProviderClient =
        LocalObjectStorageProviderClient()

    @Bean
    @ConditionalOnProperty(prefix = PREFIX, name = [PROPERTY_NAME_PROVIDER], havingValue = "s3")
    fun s3ObjectStorageProviderClient(
        s3Client: S3Client,
        s3ClientProperties: S3ClientProperties,
    ): ObjectStorageProviderClient = S3ObjectStorageProviderClient(s3Client, s3ClientProperties)

    companion object {
        private const val PREFIX = "settings.object-storage"
        private const val PROPERTY_NAME_PROVIDER = "provider"
    }
}
