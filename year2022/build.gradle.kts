dependencies {
    implementation(project(":framework"))
    implementation(project(":util"))

    testImplementation(kotlin("test"))
    testImplementation(testFixtures(project(":framework")))
}