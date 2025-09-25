plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":shared-domains"))
    api(project(":shared-clients"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Validation
    implementation("jakarta.validation:jakarta.validation-api")
    implementation(libs.hibernate.validator)

    // Tools
    implementation(libs.kotlin.logging)

    testImplementation(libs.mockk.lib)
    testImplementation(libs.mockk.spring)

    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.kotest.spring)

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "junit")
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") { enabled = false }
