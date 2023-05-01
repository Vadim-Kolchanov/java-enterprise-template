<h1 align="center">Java Enterprise Template</h1>

Java Enterprise Template is a boilerplate project that enables developers to start enterprise-level projects quickly. It
is a lightweight and modular template that utilizes the best practices and design patterns for enterprise applications.
The project is built on top of Java/Kotlin and Spring Framework and provides an opinionated, easy-to-use, and scalable
structure to build enterprise-level applications.

# Contents

- [Stack](#stack)
- [Project structure](#project-structure)
- [Documentation API](#documentation-api)
- [How to build locally](#how-to-build-locally)
- [SonarQube](#sonarqube)
- [Contributing](#contributing)
- [Show your support](#show-your-support)

## Stack

* `Kotlin` based on `Java 17`
* Build automation tool is `Gradle`
* `Spring Framework` (Boot, Cloud, Data JPA, Web, Test, Kafka, Quartz)
* Using `PostgreSQL` database with migration tool - `Flyway`
* Unit and integration testing with `JUnit` and `Mockito`
* `Swagger UI` for API documentation
* `Docker compose` includes: `Postgres`, `SonarQube`, `Zookeeper`, `Kafka`

## Project structure

Libraries and plugins along with their versions are in the
file `libs.versions.toml` [(documentation)](https://docs.gradle.org/current/userguide/platforms.html#sub:conventional-dependencies-toml).

The project is based on microservice architecture. Repository root directories can be divided into types:

* `lib-*` - libraries, starters, auto-configurations for use in microservices
* `ms-*` - microservice module, contains 2 submodules:
    * `ms-*-api` - module that provides the REST API endpoints for the application. It contains the interface API, DTOs,
      and request/response models
    * `ms-*-core` - module that contains the business logic and data access for the application. It depends on the
      ms-api module for the REST API endpoints.

## Documentation API

Project provides API documentation using Swagger UI. You can access the Swagger UI by running a microservice and
navigating to the following URL:

```
http://localhost:${MS_SERVER_PORT}/swagger-ui/index.html#/
```

## How to build locally

The assembly of the main `core` module mainly depends on the `api` module, as well as the project's shared
modules/libraries.

1) run command `./gradlew clean publishMavenPublicationToMavenLocal` - **for module**/**from directory** `ms-*-api`
  or `lib-*`
2) make sure the version in the `toml` file matches the version from the first point
3) run `Reload gradle project` on `core` module (to pull this API version from local maven)

## SonarQube

SonarQube is a popular open-source platform that provides continuous code inspection and quality analysis for
applications. It is designed to help developers improve the quality, maintainability, and reliability of their code by
identifying and reporting issues such as code smells, bugs, vulnerabilities, and technical debt.

For the microservice you want to analyze, you need to specify in `build.gradle.kts`:

```groovy
plugins {
    alias(libs.plugins.sonarqubePlugin)
}

sonarqube {
    properties {
        property("sonar.sourceEncoding", "UTF-8")
    }
}
```

Command to start analysis:

```shell
`gradle sonarqube -Dsonar.login=<login> -Dsonar.host.url=<url>`
```

where for `-Dsonar.login` we specify the token that was generated in SonarQube; for `-Dsonar.host.url` we specify the
host on which SonarQube is deployed.

---

## Contributing

If you find a bug or want to contribute to the project, please feel free to create a pull request or open an issue.

## Show your support

Please ⭐️ this repository if this project helped you!

## 📝License

Copyright © 2023 [Vadim Kolchanov](https://github.com/Vadim-Kolchanov).<br />
Java Enterprise Template is open-source software licensed under the [MIT license](./LICENSE).