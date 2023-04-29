package com.jet.common.configs.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer

abstract class KafkaAbstractConfig(
    private val kafkaProperties: KafkaProperties
) {

    open fun <V> producerFactory(
        jsonSerializer: JsonSerializer<V> = kafkaJsonSerializer(),
    ): ProducerFactory<String, V> {
        val properties: MutableMap<String, Any> = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
        ).toMutableMap()

        if (kafkaProperties.jaas.isEnabled) {
            properties[CommonClientConfigs.SECURITY_PROTOCOL_CONFIG] = kafkaProperties.security.protocol
            properties.putAll(kafkaProperties.jaas.options)
        }

        return DefaultKafkaProducerFactory(properties, StringSerializer(), jsonSerializer)
    }

    inline fun <reified V> consumerFactory(
        kafkaProperties: KafkaProperties,
    ): ConsumerFactory<String, V> {
        val properties: MutableMap<String, Any> = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            ConsumerConfig.GROUP_ID_CONFIG to kafkaProperties.consumer.groupId,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to AUTO_OFFSET_RESET_CONFIG_VALUE,
        ).toMutableMap()

        if (kafkaProperties.jaas.isEnabled) {
            properties[CommonClientConfigs.SECURITY_PROTOCOL_CONFIG] = kafkaProperties.security.protocol
            properties.putAll(kafkaProperties.jaas.options)
        }

        return DefaultKafkaConsumerFactory(
            properties,
            StringDeserializer(),
            JsonDeserializer<V>(jacksonTypeRef<V>())
        )
    }

    open fun <V> kafkaTemplate(
        producerFactory: ProducerFactory<String, V>,
    ): KafkaTemplate<String, V> = KafkaTemplate(producerFactory)

    inline fun <reified V> kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, V>
    ): ConcurrentKafkaListenerContainerFactory<String, V> = ConcurrentKafkaListenerContainerFactory<String, V>()
        .apply { this.consumerFactory = consumerFactory }

    fun <V> kafkaJsonSerializer(): JsonSerializer<V> = JsonSerializer(kafkaObjectMapper())

    private fun kafkaObjectMapper(): ObjectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    companion object {
        const val AUTO_OFFSET_RESET_CONFIG_VALUE = "earliest"
    }
}
