package com.jet.notification.entities.pushnotification

import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.jet.common.entities.UUIDAbstractEntity
import com.jet.exceptionhandler.exceptions.common.IllegalStateBaseException
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "push_notification")
class PushNotificationEntity(
    id: UUID = UUID.randomUUID(),
    val firebaseRegistrationToken: String?,
    val topicName: String?,
    val title: String,
    val message: String,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
) : UUIDAbstractEntity(id) {

    fun firebaseNotification(): Notification = Notification.builder()
        .setTitle(title)
        .setBody(message)
        .build()

    fun firebaseMessageToTopic(params: Map<String, String>): Message {
        if (topicName == null)
            throw IllegalStateBaseException(message = "Error building firebase message to topic. Topic must not be null!")

        return firebaseMessageBuilder(params)
            .setTopic(topicName)
            .build()
    }

    fun firebaseMessageToToken(params: Map<String, String>): Message {
        if (firebaseRegistrationToken == null)
            throw IllegalStateBaseException(message = "Error building firebase message to token. Firebase registration token must not be null!")

        return firebaseMessageBuilder(params)
            .setToken(firebaseRegistrationToken)
            .build()
    }

    private fun firebaseMessageBuilder(params: Map<String, String>): Message.Builder = Message.builder()
        .setNotification(firebaseNotification())
        .also { msgBuilder -> params.forEach { msgBuilder.putData(it.key, it.value) } }
}
