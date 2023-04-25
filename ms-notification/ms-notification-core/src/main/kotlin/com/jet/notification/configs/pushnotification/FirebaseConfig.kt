package com.jet.notification.configs.pushnotification

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(prefix = "settings.push-notification", name = ["provider"], havingValue = "firebase")
class FirebaseConfig {

    /**
     * For authorizing set the environment variable GOOGLE_APPLICATION_CREDENTIALS
     * to the file path of the JSON file that contains your service account key
     */
    @Bean
    fun firebaseOptions(): FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.getApplicationDefault())
        .build()

    @Bean
    fun firebaseApp(options: FirebaseOptions): FirebaseApp = FirebaseApp.initializeApp(options)

    @Bean
    fun firebaseMessaging(firebaseApp: FirebaseApp): FirebaseMessaging = FirebaseMessaging.getInstance(firebaseApp)
}
