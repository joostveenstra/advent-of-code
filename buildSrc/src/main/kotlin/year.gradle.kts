plugins {
    id("advent-of-code")
}

val libs = versionCatalogs.named("libs")

dependencies {
    implementation(project(":framework"))
    implementation(project(":util"))
    implementation(libs.findLibrary("kotlinx-collections-immutable").get())
}