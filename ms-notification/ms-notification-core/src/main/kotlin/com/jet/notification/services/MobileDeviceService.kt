package com.jet.notification.services

import com.jet.notification.domain.pushnotification.PushNotificationTopicCode
import com.jet.notification.domain.pushnotification.PushNotificationTopicCode.Companion.toPushNotificationTopicCode
import com.jet.notification.dto.mobiledevice.RegisterOrUpdateMobileDeviceRequest
import com.jet.notification.entities.MobileDeviceEntity
import com.jet.notification.exceptions.MobileDeviceNotFoundByDeviceIdException
import com.jet.notification.mappers.MobileDeviceDTOMapper.toMobileDeviceEntity
import com.jet.notification.providers.PushNotificationProviderClient
import com.jet.notification.repositories.MobileDeviceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MobileDeviceService(
    private val mobileDeviceRepository: MobileDeviceRepository,
    private val pushNotificationTopicService: PushNotificationTopicService,
    private val pushNotificationProviderClient: PushNotificationProviderClient,
) {

    @Transactional(readOnly = true)
    fun findByDeviceId(deviceId: String): MobileDeviceEntity =
        mobileDeviceRepository.findByDeviceId(deviceId) ?: throw MobileDeviceNotFoundByDeviceIdException(deviceId)

    @Transactional
    fun registerOrUpdateMobileDevice(request: RegisterOrUpdateMobileDeviceRequest): MobileDeviceEntity =
        mobileDeviceRepository.findByDeviceId(request.deviceId)
            ?.let { mobileDevice -> updateMobileDevice(mobileDevice, request) }
            ?: registerMobileDevice(request)

    @Transactional
    fun deleteMobileDeviceByDeviceId(deviceId: String): Unit = findByDeviceId(deviceId)
        .let(this::deleteMobileDevice)

    private fun registerMobileDevice(request: RegisterOrUpdateMobileDeviceRequest): MobileDeviceEntity =
        request.toMobileDeviceEntity(
            topics = pushNotificationTopicService.findTopicsByCodes(
                codes = listOf(
                    PushNotificationTopicCode.ALL,
                    request.mobilePlatform.toPushNotificationTopicCode()
                )
            )
        ).let(mobileDeviceRepository::save).subscribeToTopics()

    private fun updateMobileDevice(
        mobileDevice: MobileDeviceEntity,
        request: RegisterOrUpdateMobileDeviceRequest
    ): MobileDeviceEntity = updateFirebaseRegistrationToken(mobileDevice, request.firebaseRegistrationToken)
        .let(mobileDeviceRepository::save)

    private fun updateFirebaseRegistrationToken(mobileDevice: MobileDeviceEntity, newToken: String): MobileDeviceEntity =
        mobileDevice
            .also { if (it.firebaseRegistrationToken == newToken) return it }
            .unsubscribeFromTopics()
            .updateFirebaseRegistrationToken(newToken)
            .subscribeToTopics()

    private fun deleteMobileDevice(mobileDevice: MobileDeviceEntity): Unit = mobileDevice
        .unsubscribeFromTopics()
        .let(mobileDeviceRepository::delete)

    private fun MobileDeviceEntity.subscribeToTopics(): MobileDeviceEntity =
        apply(pushNotificationProviderClient::subscribeToTopics)

    private fun MobileDeviceEntity.unsubscribeFromTopics(): MobileDeviceEntity =
        apply(pushNotificationProviderClient::unsubscribeFromTopics)
}
