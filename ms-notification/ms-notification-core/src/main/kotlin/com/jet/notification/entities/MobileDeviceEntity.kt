package com.jet.notification.entities

import com.jet.common.entities.UUIDAbstractEntity
import com.jet.notification.domain.mobiledevice.MobilePlatform
import com.jet.notification.entities.pushnotification.PushNotificationTopicEntity
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "mobile_device")
class MobileDeviceEntity(
    id: UUID = UUID.randomUUID(),
    @Column(unique = true)
    val deviceId: String,
    @Enumerated(EnumType.STRING)
    val mobilePlatform: MobilePlatform,
    var firebaseRegistrationToken: String,
    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "mobile_device_push_notification_topic",
        joinColumns = [JoinColumn(name = "mobile_device_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "push_notification_topic_id", referencedColumnName = "id")]
    )
    val topics: MutableSet<PushNotificationTopicEntity> = mutableSetOf(),
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    var updatedAt: OffsetDateTime = createdAt,
    var firebaseRegistrationTokenUpdatedAt: OffsetDateTime = createdAt,
) : UUIDAbstractEntity(id) {

    fun updateFirebaseRegistrationToken(firebaseRegistrationToken: String): MobileDeviceEntity = apply {
        val updatedAt: OffsetDateTime = OffsetDateTime.now()

        this.firebaseRegistrationToken = firebaseRegistrationToken
        this.firebaseRegistrationTokenUpdatedAt = updatedAt
        this.updatedAt = updatedAt
    }
}
