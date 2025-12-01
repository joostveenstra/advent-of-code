internal object Templates {
    fun generateDay(year: Int, day: String) =
        """
            package year${year}

            import framework.Context
            import framework.Day

            class Day${day}(context: Context) : Day by context {
                fun part1() = 0
                fun part2() = 0
            }
        """.trimIndent()

    fun generateDayTest(year: Int, day: String) =
        """
            package year${year}

            import framework.Test

            class Day${day}Test : Test({
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
            plugins {
                year
            }
        """.trimIndent()
}