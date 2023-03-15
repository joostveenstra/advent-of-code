plugins {
    `java-test-fixtures`
}

dependencies {
    testFixturesImplementation("org.junit.jupiter:junit-jupiter:5.6.3")
    testFixturesImplementation(kotlin("reflect"))
    testFixturesImplementation(kotlin("test"))
}