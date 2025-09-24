plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    api(project(":shared-domains"))
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
