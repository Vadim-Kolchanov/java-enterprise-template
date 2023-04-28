package com.jet.notification.services.pushnotification

import com.jet.notification.domain.pushnotification.PushNotificationTopicCode
import com.jet.notification.dto.pushnotification.PushNotificationTopicDTO
import com.jet.notification.entities.pushnotification.PushNotificationTopicEntity
import com.jet.notification.exceptions.PushNotificationTopicNotFoundException
import com.jet.notification.mappers.PushNotificationDTOMapper.toPushNotificationTopicDTO
import com.jet.notification.repositories.pushnotification.PushNotificationTopicRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PushNotificationTopicService(
    private val pushNotificationTopicRepository: PushNotificationTopicRepository,
) {

    @Transactional(readOnly = true)
    fun findTopicsByCodes(codes: Collection<PushNotificationTopicCode>): List<PushNotificationTopicEntity> =
        pushNotificationTopicRepository.findAllByCodeIn(codes)

    @Transactional(readOnly = true)
    fun findTopicByCode(code: PushNotificationTopicCode): PushNotificationTopicEntity =
        pushNotificationTopicRepository.findByCode(code) ?: throw PushNotificationTopicNotFoundException(code)

    @Transactional(readOnly = true)
    fun getPushNotificationTopics(): List<PushNotificationTopicDTO> =
        pushNotificationTopicRepository.findAll().map { it.toPushNotificationTopicDTO() }
}
