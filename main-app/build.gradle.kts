plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

springBoot { mainClass.set("com.barret73.springboot.ApplicationKt") }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation(project(":accounts"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}