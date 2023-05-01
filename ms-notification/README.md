# ms-notification

The microservice module is responsible for sending notifications to users, such as push notifications. It also includes
a set of pre-configured notification providers that can be easily extended or replaced to suit your project's needs.

The API for sending push notifications is represented by several options:

* Using the standard [REST API](./ms-notification-api/src/main/kotlin/com/jet/notification/api/PushNotificationApi.kt)
* Using [Feign Client](./ms-notification-api/src/main/kotlin/com/jet/notification/feign/PushNotificationFeignClient.kt)
  to send notifications from other microservices
* Using the Kafka message queue. It uses an asynchronous messaging model where notifications are handled by
  a [listener](./ms-notification-core/src/main/kotlin/com/jet/notification/listeners/PushNotificationListener.kt)
  that sends the actual notification to the user. **This is the best way**

## Depends on internal modules

* [lib-common](../lib-common)
* [lib-exception-handler](../lib-exception-handler)
* [lib-logging](../lib-logging)
* [lib-test](../lib-test)

## Environment variables

| Variable name                                                                          | Default value   |
|----------------------------------------------------------------------------------------|-----------------|
| POSTGRES_URL                                                                           |                 |
| POSTGRES_USER                                                                          |                 |
| POSTGRES_PASSWORD                                                                      |                 |
| SPRING_FLYWAY_ENABLED                                                                  | true            |
| KAFKA_BOOTSTRAP_SERVERS                                                                | localhost:29092 |
| KAFKA_SECURITY_PROTOCOL                                                                | SASL_PLAINTEXT  |
| KAFKA_JAAS_ENABLED                                                                     | false           |
| KAFKA_USERNAME                                                                         |                 |
| KAFKA_PASSWORD                                                                         |                 |
| SETTINGS_ENV                                                                           |                 |
| SETTINGS_LOGGING_LEVEL                                                                 | INFO            |
| SETTINGS_CRON_PUSH_NOTIFICATION_VALIDATE_FIREBASE_REGISTRATION_TOKEN                   | 0 0 0 ? * *     |
| SETTINGS_PUSH_NOTIFICATION_PROVIDER                                                    | local           |
| SETTINGS_PUSH_NOTIFICATION_FIREBASE_REGISTRATION_TOKEN_EXPIRES_IN_DAYS                 | 30              |
| SETTINGS_PUSH_NOTIFICATION_BATCH_SIZE_FOR_DELETING_EXPIRED_FIREBASE_REGISTRATION_TOKEN | 100             |
| GOOGLE_APPLICATION_CREDENTIALS                                                         |                 |

## [How to build locally](../README.md)

## How to run locally

1) Have connection to `PostgreSQL`
2) Have running `Kafka`
3) Required variables that do not have default values are specified
4) [Run application](./ms-notification-core/src/main/kotlin/com/jet/notification/NotificationApplication.kt)

## Notes

* To select a provider for sending push notifications, specify the available value
from [PushNotificationProviderConfig](./ms-notification-core/src/main/kotlin/com/jet/notification/configs/pushnotification/PushNotificationProviderConfig.kt)
in the `SETTINGS_PUSH_NOTIFICATION_PROVIDER` variable. When specifying the value of `firebase`, you must set the
`GOOGLE_APPLICATION_CREDENTIALS` variable to the file path of the JSON file that contains your Firebase service account
key.
