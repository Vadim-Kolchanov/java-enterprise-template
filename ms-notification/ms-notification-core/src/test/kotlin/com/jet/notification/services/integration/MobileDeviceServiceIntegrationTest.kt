package com.jet.notification.services.integration

import com.jet.notification.domain.pushnotification.PushNotificationTopicCode
import com.jet.notification.domain.pushnotification.PushNotificationTopicCode.Companion.toPushNotificationTopicCode
import com.jet.notification.entities.MobileDeviceEntity
import com.jet.notification.exceptions.MobileDeviceNotFoundByDeviceIdException
import com.jet.notification.helpers.builders.MobileDeviceBuilder.registerOrUpdateMobileDeviceRequest
import com.jet.notification.helpers.constants.MobileDeviceConstant.DEVICE_ID
import com.jet.notification.helpers.constants.MobileDeviceConstant.FIREBASE_REGISTRATION_TOKEN
import com.jet.notification.helpers.constants.MobileDeviceConstant.MOBILE_PLATFORM
import com.jet.notification.providers.PushNotificationProviderClient
import com.jet.notification.services.MobileDeviceService
import com.jet.test.annotations.WithPostgres
import com.ninjasquad.springmockk.MockkBean
import io.mockk.justRun
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

@WithPostgres
@SpringBootTest
class MobileDeviceServiceIntegrationTest {

    @Autowired
    lateinit var mobileDeviceService: MobileDeviceService

    @MockkBean
    lateinit var pushNotificationProviderClient: PushNotificationProviderClient

    @Nested
    open inner class RegisterOrUpdateMobileDevice {

        @Test
        @Transactional
        @Rollback(true)
        open fun `Should successfully register mobile device`() {
            justRun { pushNotificationProviderClient.subscribeToTopics(any()) }

            val mobileDevice: MobileDeviceEntity = mobileDeviceService.registerOrUpdateMobileDevice(
                request = registerOrUpdateMobileDeviceRequest()
            )

            assertThat(mobileDevice).satisfies(Consumer {
                assertThat(it.deviceId).isEqualTo(DEVICE_ID)
                assertThat(it.mobilePlatform).isEqualTo(MOBILE_PLATFORM)
                assertThat(it.firebaseRegistrationToken).isEqualTo(FIREBASE_REGISTRATION_TOKEN)
                assertThat(it.topics).allSatisfy(Consumer { topic ->
                    assertThat(topic.code).isIn(PushNotificationTopicCode.ALL, MOBILE_PLATFORM.toPushNotificationTopicCode())
                })
            })
            verifyOrder {
                pushNotificationProviderClient.subscribeToTopics(any())
            }
        }

        @Test
        @Transactional
        @Rollback(true)
        open fun `Should successfully update mobile device`() {
            justRun { pushNotificationProviderClient.subscribeToTopics(any()) }
            justRun { pushNotificationProviderClient.unsubscribeFromTopics(any()) }

            val registeredMobileDevice: MobileDeviceEntity = mobileDeviceService.registerOrUpdateMobileDevice(
                request = registerOrUpdateMobileDeviceRequest(
                    firebaseRegistrationToken = FIREBASE_REGISTRATION_TOKEN
                )
            )
            assertThat(registeredMobileDevice).satisfies(Consumer {
                assertThat(it.deviceId).isEqualTo(DEVICE_ID)
                assertThat(it.firebaseRegistrationToken).isEqualTo(FIREBASE_REGISTRATION_TOKEN)
            })

            val newToken = "newFirebaseRegistrationToken"
            val updatedMobileDevice: MobileDeviceEntity = mobileDeviceService.registerOrUpdateMobileDevice(
                request = registerOrUpdateMobileDeviceRequest(
                    firebaseRegistrationToken = newToken
                )
            )

            assertThat(updatedMobileDevice).satisfies(Consumer {
                assertThat(it.id).isEqualTo(registeredMobileDevice.id)
                assertThat(it.deviceId).isEqualTo(registeredMobileDevice.deviceId)
                assertThat(it.mobilePlatform).isEqualTo(registeredMobileDevice.mobilePlatform)
                assertThat(it.firebaseRegistrationToken).isEqualTo(newToken)

                assertThat(it.firebaseRegistrationToken).isNotEqualTo(FIREBASE_REGISTRATION_TOKEN)
            })
            verifyOrder {
                pushNotificationProviderClient.subscribeToTopics(any())
                pushNotificationProviderClient.unsubscribeFromTopics(any())
                pushNotificationProviderClient.subscribeToTopics(any())
            }
        }
    }

    @Nested
    open inner class DeleteMobileDeviceByDeviceId {

        @Test
        @Transactional
        @Rollback(true)
        open fun `Should successfully delete mobile device`() {
            justRun { pushNotificationProviderClient.subscribeToTopics(any()) }
            justRun { pushNotificationProviderClient.unsubscribeFromTopics(any()) }

            val mobileDevice: MobileDeviceEntity = mobileDeviceService.registerOrUpdateMobileDevice(
                request = registerOrUpdateMobileDeviceRequest()
            )
            assertThat(mobileDevice.deviceId).isEqualTo(DEVICE_ID)

            mobileDeviceService.deleteMobileDeviceByDeviceId(mobileDevice.deviceId)

            assertThrows<MobileDeviceNotFoundByDeviceIdException> {
                mobileDeviceService.findByDeviceId(mobileDevice.deviceId)
            }
            verifyOrder {
                pushNotificationProviderClient.subscribeToTopics(any())
                pushNotificationProviderClient.unsubscribeFromTopics(any())
            }
        }
    }
}
