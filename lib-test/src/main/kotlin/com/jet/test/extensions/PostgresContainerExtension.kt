package com.jet.test.extensions

import com.jet.test.extensions.PostgresContainerExtension.PostgresEnvironment.*
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

open class PostgresContainerExtension : BeforeAllCallback {

    private enum class PostgresEnvironment(
        default: String
    ) {
        POSTGRES_IMAGE("postgres:14-alpine"),
        POSTGRES_USER("testuser"),
        POSTGRES_PASSWORD("testpass"),
        POSTGRES_DB("test"),
        POSTGRES_PORT("5432");

        val value: String = System.getenv("TEST_$name") ?: default
    }

    override fun beforeAll(extensionContext: ExtensionContext) {
        postgresContainer.start()

        System.setProperty(
            "spring.datasource.url",
            "jdbc:postgresql://localhost:${postgresContainer.firstMappedPort}/${POSTGRES_DB.value}"
        )
        System.setProperty("spring.datasource.username", POSTGRES_USER.value)
        System.setProperty("spring.datasource.password", POSTGRES_PASSWORD.value)
    }

    companion object {
        private val postgresContainer =
            GenericContainer(DockerImageName.parse(POSTGRES_IMAGE.value))
                .withEnv(POSTGRES_USER.name, POSTGRES_USER.value)
                .withEnv(POSTGRES_PASSWORD.name, POSTGRES_PASSWORD.value)
                .withEnv(POSTGRES_DB.name, POSTGRES_DB.value)
                .withExposedPorts(POSTGRES_PORT.value.toInt())
    }
}
