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
    alias(libs.plugins.springBootPlugin)
    alias(libs.plugins.springDependencyManagementPlugin)
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
    implementation(libs.libGatewayStarter)
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
