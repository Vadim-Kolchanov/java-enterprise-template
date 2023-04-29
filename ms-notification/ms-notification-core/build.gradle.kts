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
    alias(libs.plugins.sonarqubePlugin)
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
    implementation(libs.springBootStarterQuartz)

    implementation(libs.flyway)
    runtimeOnly(libs.postgresql)

    implementation(libs.kotlinJdk8)

    implementation(libs.firebaseAdmin)

    testImplementation(libs.libTest)
    testImplementation(libs.springBootStarterTest) {
        exclude(group = "org.junit.jupiter", module = "junit-jupiter")
        exclude(group = "org.mockito", module = "mockito-core")
        exclude(group = "org.mockito", module = "mockito-junit-jupiter")
    }
    testImplementation(libs.mockk)
    testImplementation(libs.springMockk)
    testImplementation(libs.junitApi)
    testImplementation(libs.junitParams)
    testRuntimeOnly(libs.junitEngine)
}

sonarqube {
    properties {
        property("sonar.sourceEncoding", "UTF-8")
    }
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
