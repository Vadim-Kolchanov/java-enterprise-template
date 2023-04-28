package com.jet.notification.configs.pushnotification

import com.jet.common.utils.QuartzUtils
import com.jet.notification.jobs.ValidateFirebaseRegistrationTokenJob
import org.quartz.CronScheduleBuilder
import org.quartz.JobDetail
import org.quartz.Trigger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ValidateFirebaseRegistrationTokenJobConfig(
    @Value("\${settings.cron.push-notification.validate-firebase-registration-token}")
    private val validateFirebaseRegistrationTokenCron: String
) {

    @Bean
    fun validateFirebaseRegistrationTokenJobDetail(): JobDetail =
        QuartzUtils.buildJobDetail(ValidateFirebaseRegistrationTokenJob::class.java)

    @Bean
    fun validateFirebaseRegistrationTokenTrigger(validateFirebaseRegistrationTokenJobDetail: JobDetail): Trigger {
        return QuartzUtils.buildJobTrigger(
            validateFirebaseRegistrationTokenJobDetail,
            CronScheduleBuilder.cronSchedule(validateFirebaseRegistrationTokenCron)
        )
    }
}
