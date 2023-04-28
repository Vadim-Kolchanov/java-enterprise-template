package com.jet.notification.dto.pushnotification

import com.jet.notification.domain.pushnotification.PushNotificationTopicCode
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "Push notification topic")
data class PushNotificationTopicDTO(
    @field:Schema(description = "Identifier topic")
    val id: UUID,
    @field:Schema(description = "Code")
    val code: PushNotificationTopicCode,
    @field:Schema(description = "Description")
    val description: String,
)
