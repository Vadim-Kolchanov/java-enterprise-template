package com.jet.objectstorage.properties

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "settings.object-storage.s3-client")
@ConditionalOnProperty(prefix = "settings.object-storage", name = ["provider"], havingValue = "s3")
class S3ClientProperties(
    val url: String,
    val accessKeyId: String,
    val secretAccessKey: String,
    val bucket: String,
    val region: String,
)
