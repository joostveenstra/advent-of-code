dependencies {
    implementation(project(":year"))

    testImplementation(kotlin("test"))
    testImplementation(testFixtures(project(":framework")))
}