import kotlin.io.path.*

internal class FileHelper(private val year: Int, private val day: Int) {
    private val baseDir = Path("year$year")
    private val sourceDir = baseDir / "src/main/kotlin"
    private val testSourceDir = baseDir / "src/test/kotlin"
    private val testResourcesDir = baseDir / "src/test/resources/day$day"

    private fun createDirectories() =
        listOf(sourceDir, testSourceDir, testResourcesDir).forEach { it.createDirectories() }

    fun createFiles() {
        val build = baseDir / "build.gradle.kts"
        val source = sourceDir / "Day${day}.kt"
        val test = testSourceDir / "Day${day}Test.kt"
        val example = testResourcesDir / "example.txt"
        val input = testResourcesDir / "input.txt"

        createDirectories()

        if (build.notExists())
            build.writeText(Templates.generateBuildGradle())

        source.writeText(Templates.generateDay(day))
        test.writeText(Templates.generateDayTest(day))
        example.writeText("")
        input.writeText(InputFetcher(year, day).execute())
    }
}