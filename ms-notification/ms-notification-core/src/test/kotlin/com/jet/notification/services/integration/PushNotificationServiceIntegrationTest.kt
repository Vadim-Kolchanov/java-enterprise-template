package com.jet.notification.services.integration

import com.jet.notification.entities.MobileDeviceEntity
import com.jet.notification.entities.pushnotification.PushNotificationEntity
import com.jet.notification.exceptions.InvalidFirebaseRegistrationTokenException
import com.jet.notification.exceptions.MobileDeviceNotFoundByDeviceIdException
import com.jet.notification.helpers.builders.MobileDeviceBuilder.registerOrUpdateMobileDeviceRequest
import com.jet.notification.helpers.builders.PushNotificationBuilder.sendPushNotificationToMobileDeviceRequest
import com.jet.notification.helpers.builders.PushNotificationBuilder.sendPushNotificationToTopicRequest
import com.jet.notification.helpers.constants.PushNotificationConstants.MESSAGE
import com.jet.notification.helpers.constants.PushNotificationConstants.TITLE
import com.jet.notification.providers.PushNotificationProviderClient
import com.jet.notification.services.MobileDeviceService
import com.jet.notification.services.pushnotification.PushNotificationService
import com.jet.test.annotations.WithPostgres
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verifyOrder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.util.function.Consumer
import javax.persistence.EntityManager

@WithPostgres
@SpringBootTest
class PushNotificationServiceIntegrationTest {

    @Autowired
    lateinit var pushNotificationService: PushNotificationService

    @Autowired
    lateinit var mobileDeviceService: MobileDeviceService

    @Autowired
    lateinit var entityManager: EntityManager

    @MockkBean
    lateinit var pushNotificationProviderClient: PushNotificationProviderClient

    @Nested
    open inner class SendPushNotificationToTopic {

        @Test
        @Transactional
        @Rollback(true)
        open fun `Should successfully send push notification to topic`() {
            justRun { pushNotificationProviderClient.sendPushNotificationToTopic(any(), any()) }

            val pushNotification: PushNotificationEntity = pushNotificationService.sendPushNotificationToTopic(
                request = sendPushNotificationToTopicRequest()
            )

            assertThat(pushNotification).satisfies(Consumer {
                assertThat(it.title).isEqualTo(TITLE)
                assertThat(it.message).isEqualTo(MESSAGE)
            })

            verifyOrder {
                pushNotificationProviderClient.sendPushNotificationToTopic(pushNotification, any())
            }
        }
    }

    @Nested
    open inner class SendPushNotificationToMobileDevice {

        @Test
        @Transactional
        @Rollback(true)
        open fun `Should successfully send push notification to mobile device`() {
            justRun { pushNotificationProviderClient.subscribeToTopics(any()) }
            justRun { pushNotificationProviderClient.sendPushNotificationToToken(any(), any()) }

            val mobileDevice: MobileDeviceEntity = mobileDeviceService.registerOrUpdateMobileDevice(
                request = registerOrUpdateMobileDeviceRequest()
            )

            val pushNotification: PushNotificationEntity = pushNotificationService.sendPushNotificationToMobileDevice(
                request = sendPushNotificationToMobileDeviceRequest()
            )

            assertThat(pushNotification).satisfies(Consumer {
                assertThat(it.title).isEqualTo(TITLE)
                assertThat(it.message).isEqualTo(MESSAGE)
                assertThat(it.firebaseRegistrationToken).isEqualTo(mobileDevice.firebaseRegistrationToken)
            })

            verifyOrder {
                pushNotificationProviderClient.subscribeToTopics(any())
                pushNotificationProviderClient.sendPushNotificationToToken(pushNotification, any())
            }
        }

        @Test
        @Transactional
        @Rollback(true)
        open fun `Should throw exception if firebase registration token is not valid`() {
            justRun { pushNotificationProviderClient.subscribeToTopics(any()) }
            justRun { pushNotificationProviderClient.unsubscribeFromTopics(any()) }

            val mobileDevice: MobileDeviceEntity = mobileDeviceService.registerOrUpdateMobileDevice(
                request = registerOrUpdateMobileDeviceRequest()
            )

            every {
                pushNotificationProviderClient.sendPushNotificationToToken(any(), any())
            } throws InvalidFirebaseRegistrationTokenException(
                firebaseRegistrationToken = mobileDevice.firebaseRegistrationToken,
                cause = mockk(),
            )
            assertThrows<InvalidFirebaseRegistrationTokenException> {
                pushNotificationService.sendPushNotificationToMobileDevice(
                    request = sendPushNotificationToMobileDeviceRequest(
                        deviceId = mobileDevice.deviceId
                    )
                )
            }

            // Should not save push notification
            val pushNotifications: List<PushNotificationEntity> = entityManager
                .createQuery("SELECT pn FROM PushNotificationEntity pn", PushNotificationEntity::class.java)
                .resultList
            assertThat(pushNotifications).isEmpty()

            // Should delete mobile device, because it has not valid token
            assertThrows<MobileDeviceNotFoundByDeviceIdException> {
                mobileDeviceService.findByDeviceId(deviceId = mobileDevice.deviceId)
            }
        }
    }
}
