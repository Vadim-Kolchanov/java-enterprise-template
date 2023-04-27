package com.jet.notification.providers.impl

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.MessagingErrorCode
import com.jet.exceptionhandler.exceptions.common.IllegalStateBaseException
import com.jet.notification.entities.MobileDeviceEntity
import com.jet.notification.entities.pushnotification.PushNotificationEntity
import com.jet.notification.entities.pushnotification.PushNotificationTopicEntity
import com.jet.notification.exceptions.FirebaseInteractionException
import com.jet.notification.exceptions.InvalidFirebaseRegistrationTokenException
import com.jet.notification.providers.PushNotificationProviderClient

class FirebasePushNotificationProviderClient(
    private val firebaseMessaging: FirebaseMessaging,
) : PushNotificationProviderClient {

    override fun subscribeToTopics(mobileDevice: MobileDeviceEntity) {
        subscribeToTopics(listOf(mobileDevice.firebaseRegistrationToken), mobileDevice.topics)
    }

    override fun unsubscribeFromTopics(mobileDevice: MobileDeviceEntity) {
        unsubscribeFromTopics(listOf(mobileDevice.firebaseRegistrationToken), mobileDevice.topics)
    }

    override fun sendPushNotificationToTopic(
        pushNotification: PushNotificationEntity,
        params: Map<String, String>,
    ) {
        try {
            firebaseMessaging.send(pushNotification.firebaseMessageToTopic(params))
        } catch (ex: Exception) {
            throw FirebaseInteractionException(
                message = "Error sending push notification to topic [${pushNotification.topicName}]",
                cause = ex
            )
        }
    }

    override fun sendPushNotificationToToken(
        pushNotification: PushNotificationEntity,
        params: Map<String, String>,
    ) {
        try {
            firebaseMessaging.send(pushNotification.firebaseMessageToToken(params))
        } catch (ex: FirebaseMessagingException) {
            handleFirebaseMessagingException(ex, pushNotification.firebaseRegistrationToken)
        } catch (ex: Exception) {
            throw FirebaseInteractionException(
                message = "Error sending push notification to token [${pushNotification.firebaseRegistrationToken}]",
                cause = ex,
            )
        }
    }

    fun subscribeToTopics(
        registrationTokens: List<String>,
        topics: Collection<PushNotificationTopicEntity>
    ) {
        topics.forEach { topic ->
            try {
                firebaseMessaging.subscribeToTopic(registrationTokens, topic.name)
            } catch (ex: Exception) {
                throw FirebaseInteractionException(
                    message = "Error when subscribing registrationTokens [$registrationTokens] to topic [$topic]",
                    cause = ex
                )
            }
        }
    }

    fun unsubscribeFromTopics(
        registrationTokens: List<String>,
        topics: Collection<PushNotificationTopicEntity>
    ) {
        topics.forEach { topic ->
            try {
                firebaseMessaging.unsubscribeFromTopic(registrationTokens, topic.name)
            } catch (ex: Exception) {
                throw FirebaseInteractionException(
                    message = "Error when unsubscribing registrationTokens [$registrationTokens] from topic [$topic]",
                    cause = ex
                )
            }
        }
    }

    /**
     * [Error codes for FCM](https://firebase.google.com/docs/reference/fcm/rest/v1/ErrorCode)
     */
    private fun handleFirebaseMessagingException(
        ex: FirebaseMessagingException,
        firebaseRegistrationToken: String?,
    ) {
        when (ex.messagingErrorCode) {
            MessagingErrorCode.INVALID_ARGUMENT,
            MessagingErrorCode.UNREGISTERED -> throw InvalidFirebaseRegistrationTokenException(
                firebaseRegistrationToken = firebaseRegistrationToken
                    ?: throw IllegalStateBaseException(message = "FirebaseRegistrationToken must not be null!"),
                cause = ex,
            )

            else -> throw ex
        }
    }
}
