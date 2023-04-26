package com.jet.test.annotations

import com.jet.test.extensions.PostgresContainerExtension
import org.junit.jupiter.api.extension.ExtendWith

@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(PostgresContainerExtension::class)
annotation class WithPostgres
