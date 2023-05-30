# ms-gateway

The microservice module that serves as the entry point for the application. It provides a gateway to the microservice module and
allows clients
to access the REST API endpoints. It uses Spring Cloud Gateway, which is a lightweight API gateway that provides
features such as routing, filtering, and load balancing.

## Depends on internal modules

* [lib-gateway-starter](../lib-gateway-starter)

## Environment variables

| Variable name                                | Default value         |
|----------------------------------------------|-----------------------|
| CORS_CONFIGURATIONS_ALLOWED_ORIGINS          | *                     |
| CORS_CONFIGURATIONS_ALLOWED_HEADERS          | *                     |
| CORS_CONFIGURATIONS_ALLOWED_METHODS          | *                     |
| SETTINGS_MS_NOTIFICATION_URL                 | http://localhost:8081 |
| SETTINGS_MS_OBJECT_STORAGE_URL               | http://localhost:8082 |
| SETTINGS_GATEWAY_FILTERS_BASIC_AUTH_USERNAME |                       |
| SETTINGS_GATEWAY_FILTERS_BASIC_AUTH_PASSWORD |                       |

## [How to build locally](../README.md)

## How to run locally

1) Required variables that do not have default values are specified
2) [Run application](./ms-gateway-core/src/main/kotlin/com/jet/gateway/GatewayApplication.kt)