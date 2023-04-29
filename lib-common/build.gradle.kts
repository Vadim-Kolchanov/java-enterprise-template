import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.jet"
version = "0.0.1"

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
}

plugins {
    `maven-publish`
    alias(libs.plugins.kotlinJvmPlugin)
    alias(libs.plugins.kotlinSpringPlugin)
    alias(libs.plugins.kotlinJpaPlugin)
}

repositories {
    mavenCentral()
    mavenLocal()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

dependencies {
    implementation(platform(libs.springBootDependencies))
    implementation(libs.springBootStarterDataJpa)
    implementation(libs.springCloudStarterFeign)
    implementation(libs.springBootStarterQuartz)
    implementation(libs.springKafka)

    implementation(libs.kotlinJdk8)
    implementation(libs.jacksonKotlinModule)
    implementation(libs.jacksonDatatype)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}
