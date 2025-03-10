plugins {
    `java-test-fixtures`
    kotlin("jvm")
}

val libs = versionCatalogs.named("libs")

dependencies {
    testFixturesImplementation(libs.findLibrary("jUnit").get())
    testFixturesImplementation(kotlin("reflect"))
    testFixturesImplementation(kotlin("test"))
}
