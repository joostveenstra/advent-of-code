plugins {
    kotlin("jvm") version "2.1.10"
}

allprojects {
    group = "nl.joostveenstra"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")

    kotlin {
        jvmToolchain(17)
    }

    tasks.test {
        useJUnitPlatform()
    }
}