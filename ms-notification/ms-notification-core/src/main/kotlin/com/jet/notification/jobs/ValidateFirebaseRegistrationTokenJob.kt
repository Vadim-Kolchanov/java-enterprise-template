package com.jet.notification.jobs

import com.jet.notification.properties.ValidateFirebaseRegistrationTokenProperty
import com.jet.notification.services.MobileDeviceService
import mu.KotlinLogging
import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

private val log = KotlinLogging.logger {}

@Component
@DisallowConcurrentExecution
class ValidateFirebaseRegistrationTokenJob(
    private val mobileDeviceService: MobileDeviceService,
    private val property: ValidateFirebaseRegistrationTokenProperty,
) : Job {

    override fun execute(context: JobExecutionContext?) {
        try {
            while (
                mobileDeviceService.deleteExpiredFirebaseRegistrationToken(
                    firebaseRegistrationTokenExpiryDateTime = firebaseRegistrationTokenExpiryDateTime(),
                    batchSize = property.batchSizeForDeletingExpiredFirebaseRegistrationToken
                )
            ) {
                // executing...
            }
        } catch (ex: Exception) {
            log.error(ex) { "Error executing validate firebase registration token job" }
        }
    }

    private fun firebaseRegistrationTokenExpiryDateTime(): OffsetDateTime =
        OffsetDateTime.now().minusDays(property.firebaseRegistrationTokenExpiresInDays)
}