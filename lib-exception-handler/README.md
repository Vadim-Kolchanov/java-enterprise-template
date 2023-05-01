# lib-exception-handler

The library for handling exceptions in the application. It provides a set of reusable exception handlers that can be
used across the project. Additionally, it provides a custom [HttpErrorDTO](./src/main/kotlin/com/jet/exceptionhandler/dto/HttpErrorDTO.kt) class to standardize error responses across
the application.

To create custom errors, inherit from [BaseException](./src/main/kotlin/com/jet/exceptionhandler/exceptions/BaseException.kt) class.