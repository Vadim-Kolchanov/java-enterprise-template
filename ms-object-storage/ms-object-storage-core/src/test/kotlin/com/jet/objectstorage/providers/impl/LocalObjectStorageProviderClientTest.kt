package com.jet.objectstorage.providers.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.util.UriComponents

class LocalObjectStorageProviderClientTest {

    @Test
    fun buildUriTest() {
        val folder = "folder"
        val fileName = "fileName"

        val uri: UriComponents = LocalObjectStorageProviderClient().buildUri(folder, fileName)

        assertThat(uri.toUriString()).isEqualTo("local:$folder/$fileName")
    }
}
