package com.jet.notification.jobs.integration

import com.jet.notification.entities.MobileDeviceEntity
import com.jet.notification.exceptions.MobileDeviceNotFoundByDeviceIdException
import com.jet.notification.helpers.builders.MobileDeviceBuilder.mobileDeviceEntity
import com.jet.notification.helpers.constants.MobileDeviceConstants.DEVICE_ID
import com.jet.notification.jobs.ValidateFirebaseRegistrationTokenJob
import com.jet.notification.properties.ValidateFirebaseRegistrationTokenProperty
import com.jet.notification.providers.PushNotificationProviderClient
import com.jet.notification.services.MobileDeviceService
import com.jet.test.annotations.WithPostgres
import com.ninjasquad.springmockk.MockkBean
import io.mockk.justRun
import io.mockk.verifyOrder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import javax.persistence.EntityManager

@WithPostgres
@SpringBootTest
internal class ValidateFirebaseRegistrationTokenJobIntegrationTest {

    @Autowired
    lateinit var validateFirebaseRegistrationTokenJob: ValidateFirebaseRegistrationTokenJob

    @Autowired
    lateinit var mobileDeviceService: MobileDeviceService

    @Autowired
    lateinit var validateFirebaseRegistrationTokenProperty: ValidateFirebaseRegistrationTokenProperty

    @Autowired
    lateinit var entityManager: EntityManager

    @MockkBean
    lateinit var pushNotificationProviderClient: PushNotificationProviderClient

    @Test
    @Transactional
    @Rollback(true)
    fun executeTest() {
        justRun { pushNotificationProviderClient.unsubscribeFromTopics(any()) }

        val deviceId2 = "deviceId2"
        val dateTimeNow: OffsetDateTime = OffsetDateTime.now()
        val expiredTokenDateTime: OffsetDateTime =
            dateTimeNow.minusDays(validateFirebaseRegistrationTokenProperty.firebaseRegistrationTokenExpiresInDays)

        val mobileDevice: MobileDeviceEntity = createMobileDeviceEntity(
            deviceId = DEVICE_ID,
            createdAt = expiredTokenDateTime,
        )
        val mobileDevice2: MobileDeviceEntity = createMobileDeviceEntity(
            deviceId = deviceId2,
            createdAt = dateTimeNow,
        )

        // before execute
        mobileDeviceService.findByDeviceId(DEVICE_ID).let {
            assertThat(it.id).isEqualTo(mobileDevice.id)
        }
        mobileDeviceService.findByDeviceId(deviceId2).let {
            assertThat(it.id).isEqualTo(mobileDevice2.id)
        }

        validateFirebaseRegistrationTokenJob.execute(context = null)

        // after execute
        assertThrows<MobileDeviceNotFoundByDeviceIdException> {
            mobileDeviceService.findByDeviceId(DEVICE_ID)
        }
        mobileDeviceService.findByDeviceId(deviceId2).let {
            assertThat(it.id).isEqualTo(mobileDevice2.id)
        }

        verifyOrder {
            pushNotificationProviderClient.unsubscribeFromTopics(any())
        }
    }

    private fun createMobileDeviceEntity(
        deviceId: String,
        createdAt: OffsetDateTime,
    ): MobileDeviceEntity = mobileDeviceEntity(
        deviceId = deviceId,
        topics = emptySet(),
        createdAt = createdAt,
        updatedAt = createdAt,
        firebaseRegistrationTokenUpdatedAt = createdAt,
    ).also(entityManager::persist)
}
