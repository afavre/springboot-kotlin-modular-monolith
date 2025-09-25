plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":main-app"))

    testImplementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation(libs.mockk.lib)
    testImplementation(libs.mockk.spring)

    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.kotest.spring)

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "junit")
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}
