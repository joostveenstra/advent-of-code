internal object Templates {
    private fun Int.prefix() = toString().padStart(2, '0')

    fun generateDay(year: Int, day: Int) =
        """
            package year${year}

            import framework.Context
            import framework.Day

            class Day${day.prefix()}(context: Context) : Day by context {
                fun part1() = TODO()
                fun part2() = TODO()
            }
        """.trimIndent()

    fun generateDayTest(year: Int, day: Int) =
        """
            package year${year}

            import framework.Test

            class Day${day.prefix()}Test : Test({
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