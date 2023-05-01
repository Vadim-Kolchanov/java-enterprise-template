# lib-test

A library that provides a set of utilities and functions for testing an application. It includes popular testing
frameworks such as JUnit and Mockito, as well as additional utilities for testing Spring applications.

The library provides a set of preconfigured test annotations:

* [@WithPostgres](./src/main/kotlin/com/jet/test/annotations/WithPostgres.kt) can be used in integration tests to bring
  up a database in a Docker container.