rootProject.name = "advent-of-code"

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":cli")
include(":framework")
include(":util")
include(":year2015")
include(":year2016")
include(":year2017")
include(":year2018")
include(":year2022")
include(":year2023")
include(":year2024")