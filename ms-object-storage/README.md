# ms-object-storage

The microservice module is #TODO

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
| SETTINGS_ENV                                                                           |                 |
| SETTINGS_LOGGING_LEVEL                                                                 | INFO            |
[//]: # (TODO...)

## [How to build locally](../README.md)

## How to run locally

1) Have connection to `PostgreSQL`
2) Required variables that do not have default values are specified
3) [Run application](./ms-object-storage-core/src/main/kotlin/com/jet/objectstorage/ObjectStorageApplication.kt)
