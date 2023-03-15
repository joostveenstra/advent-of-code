rootProject.name = "advent-of-code"

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

include(":cli")
include(":framework")
include(":util")
include(":year2022")