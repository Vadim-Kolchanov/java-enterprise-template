package com.jet.notification.services.pushnotification

import com.jet.notification.dto.pushnotification.SendPushNotificationToMobileDeviceRequest
import com.jet.notification.dto.pushnotification.SendPushNotificationToTopicRequest
import com.jet.notification.entities.pushnotification.PushNotificationEntity
import com.jet.notification.exceptions.InvalidFirebaseRegistrationTokenException
import com.jet.notification.mappers.PushNotificationDTOMapper.toPushNotificationEntity
import com.jet.notification.providers.PushNotificationProviderClient
import com.jet.notification.repositories.pushnotification.PushNotificationRepository
import com.jet.notification.services.MobileDeviceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PushNotificationService(
    private val pushNotificationRepository: PushNotificationRepository,
    private val pushNotificationTopicService: PushNotificationTopicService,
    private val pushNotificationProviderClient: PushNotificationProviderClient,
    private val mobileDeviceService: MobileDeviceService,
) {

    @Transactional(noRollbackFor = [InvalidFirebaseRegistrationTokenException::class])
    fun sendPushNotificationToMobileDevice(request: SendPushNotificationToMobileDeviceRequest): PushNotificationEntity =
        sendPushNotificationToMobileDevice(
            deviceId = request.deviceId,
            pushNotification = request.toPushNotificationEntity(
                mobileDevice = mobileDeviceService.findByDeviceId(request.deviceId)
            ),
            params = request.pushNotification.params
        )

    @Transactional
    fun sendPushNotificationToTopic(request: SendPushNotificationToTopicRequest): PushNotificationEntity =
        request.toPushNotificationEntity(topic = pushNotificationTopicService.findTopicByCode(request.topicCode))
            .let(pushNotificationRepository::save)
            .also { pushNotificationProviderClient.sendPushNotificationToTopic(it, request.pushNotification.params) }

    private fun sendPushNotificationToMobileDevice(
        deviceId: String,
        pushNotification: PushNotificationEntity,
        params: Map<String, String>,
    ): PushNotificationEntity =
        try {
            pushNotificationProviderClient.sendPushNotificationToToken(pushNotification, params)
            pushNotificationRepository.save(pushNotification)
        } catch (ex: InvalidFirebaseRegistrationTokenException) {
            mobileDeviceService.deleteMobileDeviceByDeviceId(deviceId = deviceId)
            throw ex
        }
}
