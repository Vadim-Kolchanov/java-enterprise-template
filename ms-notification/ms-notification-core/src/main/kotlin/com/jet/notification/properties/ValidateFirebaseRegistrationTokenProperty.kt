package com.jet.notification.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "settings.push-notification")
data class ValidateFirebaseRegistrationTokenProperty(
    val firebaseRegistrationTokenExpiresInDays: Long,
    val batchSizeForDeletingExpiredFirebaseRegistrationToken: Int,
)
