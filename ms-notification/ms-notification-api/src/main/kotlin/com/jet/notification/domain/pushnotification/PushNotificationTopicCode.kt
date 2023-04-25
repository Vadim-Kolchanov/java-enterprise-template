package com.jet.notification.domain.pushnotification

import com.jet.notification.domain.mobiledevice.MobilePlatform

enum class PushNotificationTopicCode {
    ALL,
    ANDROID,
    IOS;

    companion object {
        fun MobilePlatform.toPushNotificationTopicCode(): PushNotificationTopicCode = when (this) {
            MobilePlatform.ANDROID -> ANDROID
            MobilePlatform.IOS -> IOS
        }
    }
}
