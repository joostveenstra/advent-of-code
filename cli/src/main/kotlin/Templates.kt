internal object Templates {
    fun generateDay(year: Int, day: Int) =
        """
            package year${year}

            import framework.Day

            object Day${day} : Day<Int> {
                override fun part1(input: String) = TODO()

                override fun part2(input: String) = TODO()
            }
        """.trimIndent()

    fun generateDayTest(year: Int, day: Int) =
        """
            package year${year}

            import framework.DayTest

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
                implementation(project(":year"))

                testImplementation(kotlin("test"))
                testImplementation(testFixtures(project(":framework")))
            }
        """.trimIndent()
}