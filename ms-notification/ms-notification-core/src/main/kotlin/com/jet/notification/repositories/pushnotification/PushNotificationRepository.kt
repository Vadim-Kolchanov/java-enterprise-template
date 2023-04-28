package com.jet.notification.repositories.pushnotification

import com.jet.notification.entities.pushnotification.PushNotificationEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PushNotificationRepository : JpaRepository<PushNotificationEntity, UUID>
