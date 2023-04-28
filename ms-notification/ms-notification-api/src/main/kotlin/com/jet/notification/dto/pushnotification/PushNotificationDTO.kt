package com.jet.notification.dto.pushnotification

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Size

@Schema(description = "Push notification body")
data class PushNotificationDTO(
    @field:Schema(description = "Title")
    @field:Size(min = MIN_SIZE_TITLE, max = MAX_SIZE_TITLE)
    val title: String,
    @field:Schema(description = "Message")
    @field:Size(min = MIN_SIZE_MESSAGE, max = MAX_SIZE_MESSAGE)
    val message: String,
    @field:Schema(description = "Parameters", required = false)
    val params: Map<String, String> = emptyMap(),
) {
    companion object {
        const val MIN_SIZE_TITLE = 1
        const val MAX_SIZE_TITLE = 40

        const val MIN_SIZE_MESSAGE = 1
        const val MAX_SIZE_MESSAGE = 138
    }
}
