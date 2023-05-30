package com.jet.objectstorage.feign

import com.jet.objectstorage.api.FileApi
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(
    name = "fileFeignClient",
    url = "\${settings.ms.object-storage.url}"
)
interface FileFeignClient : FileApi
