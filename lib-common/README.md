# lib-common

The library that provides a set of commonly used utilities and functionality that can be used across the project. It
includes:

* Utility classes for working with [Quartz](./src/main/kotlin/com/jet/common/utils/QuartzUtils.kt)
* [AbstractEntity](./src/main/kotlin/com/jet/common/entities/AbstractEntity.kt) class that can be used as a base class
  for entities in the data access layer
* Global and base configs
  for [Feign](./src/main/kotlin/com/jet/common/configs/feign/FeignClientGlobalConfig.kt), [Kafka](./src/main/kotlin/com/jet/common/configs/kafka/KafkaAbstractConfig.kt)