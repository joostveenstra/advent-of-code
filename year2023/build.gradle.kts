dependencies {
    implementation(project(":year"))
    implementation("org.apache.commons:commons-math4-legacy:4.0-beta1")

    testImplementation(kotlin("test"))
    testImplementation(testFixtures(project(":framework")))
}