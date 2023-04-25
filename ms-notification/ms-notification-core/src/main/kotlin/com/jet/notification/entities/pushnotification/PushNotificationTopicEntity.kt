package com.jet.notification.entities.pushnotification

import com.jet.common.entities.UUIDAbstractEntity
import com.jet.notification.domain.pushnotification.PushNotificationTopicCode
import org.hibernate.annotations.Immutable
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Immutable
@Table(name = "push_notification_topic")
class PushNotificationTopicEntity(
    id: UUID = UUID.randomUUID(),
    @Enumerated(EnumType.STRING)
    val code: PushNotificationTopicCode,
    @Column(unique = true)
    val name: String,
    val description: String,
) : UUIDAbstractEntity(id)
