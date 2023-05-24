dependencies {
    implementation(project(":framework"))
    implementation(project(":util"))
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    testImplementation(kotlin("test"))
    testImplementation(testFixtures(project(":framework")))
}