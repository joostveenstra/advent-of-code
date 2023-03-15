internal object Templates {
    fun generateDay(day: Int) =
        """
            object Day${day} : Day<Any> {
                override fun part1(input: String) = TODO()

                override fun part2(input: String) = TODO()
            }
        """.trimIndent()

    fun generateDayTest(day: Int) =
        """
            class Day${day}Test : DayTest(Day${day}, {
                part1 {
                    example = 0
                    input = 0
                }

                part2 {
                    example = 0
                    input = 0
                }
            })
        """.trimIndent()

    fun generateBuildGradle() =
        """
            dependencies {
                implementation(project(":framework"))
                implementation(project(":util"))

                testImplementation(kotlin("test"))
                testImplementation(testFixtures(project(":framework")))
            }
        """.trimIndent()
}