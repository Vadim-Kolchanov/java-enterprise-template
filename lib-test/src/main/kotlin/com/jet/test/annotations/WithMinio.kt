package com.jet.test.annotations

import com.jet.test.extensions.MinioContainerExtension
import org.junit.jupiter.api.extension.ExtendWith

@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(MinioContainerExtension::class)
annotation class WithMinio
