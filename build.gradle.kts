import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("com.google.cloud.tools.jib") version "1.8.0"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
}

group = "com.github.simonenkoi"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
    maven("https://repo.spring.io/milestone")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.fu:spring-fu-kofu:0.2.2")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    compileOnly("org.springframework.boot:spring-boot-gradle-plugin:2.2.3.RELEASE")
    compileOnly("com.google.cloud.tools.jib:com.google.cloud.tools.jib.gradle.plugin:1.8.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation( "org.testcontainers:testcontainers:1.12.3")
    testImplementation("org.testcontainers:junit-jupiter:1.12.3")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

jib {
    to {
        image = "simonenkoi/spring-kotlin-chat-example"
        tags = setOf("$version")
        auth {
            username = System.getenv("DOCKERHUB_USERNAME")
            password = System.getenv("DOCKERHUB_PASSWORD")
        }
    }
    container {
        labels = mapOf(
            "maintainer" to "Ignat Simonenko <simonenkoignat@gmail.com>"
        )
        ports = listOf("8080")
    }
}
