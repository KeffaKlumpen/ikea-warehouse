plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "2.0.0-RC3"
}

group = "org.ikea"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0-RC")
}

tasks.test {
    useJUnitPlatform()
}