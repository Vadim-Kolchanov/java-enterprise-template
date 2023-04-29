package com.jet.notification.configs

import com.jet.common.configs.kafka.KafkaAbstractConfig
import com.jet.notification.dto.pushnotification.PushNotificationKafkaPayload
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory

@EnableKafka
@Configuration
class KafkaConfig(
    private val kafkaProperties: KafkaProperties
) : KafkaAbstractConfig(kafkaProperties) {

    @Bean
    fun pushNotificationConsumerFactory(): ConsumerFactory<String, PushNotificationKafkaPayload> =
        super.consumerFactory(kafkaProperties)

    @Bean
    fun pushNotificationKafkaListenerContainerFactory(
        pushNotificationConsumerFactory: ConsumerFactory<String, PushNotificationKafkaPayload>
    ): ConcurrentKafkaListenerContainerFactory<String, PushNotificationKafkaPayload> =
        super.kafkaListenerContainerFactory(pushNotificationConsumerFactory)

    companion object {
        const val PUSH_NOTIFICATION_CONTAINER_FACTORY_NAME = "pushNotificationKafkaListenerContainerFactory"
    }
}
