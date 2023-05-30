# ms-object-storage

The microservice module allows you to efficiently store and manage unstructured data. Using MinIO, it provides a
scalable and high-performance storage infrastructure for files, images, videos, and other digital assets.

## Depends on internal modules

* [lib-common](../lib-common)
* [lib-exception-handler](../lib-exception-handler)
* [lib-logging](../lib-logging)
* [lib-test](../lib-test)

## Environment variables

| Variable name                                       | Default value |
|-----------------------------------------------------|---------------|
| POSTGRES_URL                                        |               |
| POSTGRES_USER                                       |               |
| POSTGRES_PASSWORD                                   |               |
| SPRING_FLYWAY_ENABLED                               | true          |
| SPRING_SERVLET_MULTIPART_ENABLED                    | true          |
| SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE              | 128MB         |
| SPRING_SERVLET_MULTIPART_MAX_REQUEST_SIZE           | 128MB         |
| SETTINGS_ENV                                        |               |
| SETTINGS_LOGGING_LEVEL                              | INFO          |
| SETTINGS_OBJECT_STORAGE_PROVIDER                    | local         |
| SETTINGS_OBJECT_STORAGE_S3_CLIENT_URL               |               |
| SETTINGS_OBJECT_STORAGE_S3_CLIENT_ACCESS_KEY_ID     |               |
| SETTINGS_OBJECT_STORAGE_S3_CLIENT_SECRET_ACCESS_KEY |               |
| SETTINGS_OBJECT_STORAGE_S3_CLIENT_BUCKET            |               |
| SETTINGS_OBJECT_STORAGE_S3_CLIENT_REGION            |               |

## [How to build locally](../README.md)

## How to run locally

1) Have connection to `PostgreSQL`
2) Required variables that do not have default values are specified
3) [Run application](./ms-object-storage-core/src/main/kotlin/com/jet/objectstorage/ObjectStorageApplication.kt)

## Notes

* To select object storage provider, specify the available value
  from [ObjectStorageProviderConfig](./ms-object-storage-core/src/main/kotlin/com/jet/objectstorage/configs/ObjectStorageProviderConfig.kt)
  in the `SETTINGS_OBJECT_STORAGE_PROVIDER` variable