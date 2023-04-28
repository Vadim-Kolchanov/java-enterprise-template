package com.jet.notification.feign

import com.jet.notification.api.PushNotificationApi
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(
    name = "pushNotificationFeignClient",
    url = "\${settings.ms.notification.url}"
)
interface PushNotificationFeignClient: PushNotificationApi