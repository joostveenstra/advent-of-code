plugins {
    kotlin("jvm")
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(testFixtures(project(":framework")))
}

tasks.test {
    useJUnitPlatform()
}