package com.jet.notification.repositories.pushnotification

import com.jet.notification.domain.pushnotification.PushNotificationTopicCode
import com.jet.notification.entities.pushnotification.PushNotificationTopicEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PushNotificationTopicRepository : JpaRepository<PushNotificationTopicEntity, UUID> {

    fun findAllByCodeIn(codes: Collection<PushNotificationTopicCode>): List<PushNotificationTopicEntity>

    fun findByCode(code: PushNotificationTopicCode): PushNotificationTopicEntity?
}
