package com.jet.notification.repositories

import com.jet.notification.entities.MobileDeviceEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MobileDeviceRepository: JpaRepository<MobileDeviceEntity, UUID> {

    fun findByDeviceId(deviceId: String): MobileDeviceEntity?
}
