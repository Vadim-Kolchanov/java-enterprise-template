package com.jet.common.utils

import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.ScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder

object QuartzUtils {

    fun buildJobDetail(
        jobClass: Class<out Job>,
    ): JobDetail = JobBuilder.newJob(jobClass)
        .withIdentity(jobClass.simpleName)
        .storeDurably()
        .build()

    fun <T : Trigger> buildJobTrigger(
        jobDetail: JobDetail,
        scheduleBuilder: ScheduleBuilder<T>,
    ): Trigger = TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .withIdentity(jobDetail.key.name)
        .startNow()
        .withSchedule(scheduleBuilder)
        .build()
}
