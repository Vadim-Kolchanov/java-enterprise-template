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
    alias(libs.plugins.springDependencyManagementPlugin)
    alias(libs.plugins.springBootPlugin)
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
    implementation(libs.msNotificationApi)
    implementation(libs.libCommon)
    implementation(libs.libExceptionHandler)
    implementation(libs.libLogging)

    implementation(libs.springBootStarterWeb)
    implementation(libs.springBootStarterDataJpa)
//    implementation(libs.springBootStarterQuartz)

    implementation(libs.flyway)
    runtimeOnly(libs.postgresql)

    implementation(libs.kotlinJdk8)

    implementation(libs.firebaseAdmin)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
