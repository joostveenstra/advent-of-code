import kotlin.io.path.*

internal class FileHelper(private val year: Int, private val day: Int) {
    private val dayPrefixed = day.toString().padStart(2, '0')
    private val baseDir = Path("year$year")
    private val sourceDir = baseDir / "src/main/kotlin/year$year"
    private val testSourceDir = baseDir / "src/test/kotlin/year$year"
    private val testResourcesDir = baseDir / "src/test/resources/day${dayPrefixed}"

    private fun createDirectories() =
        listOf(sourceDir, testSourceDir, testResourcesDir).forEach { it.createDirectories() }

    fun createFiles() {
        val build = baseDir / "build.gradle.kts"
        val source = sourceDir / "Day${dayPrefixed}.kt"
        val test = testSourceDir / "Day${dayPrefixed}Test.kt"
        val example = testResourcesDir / "example.txt"
        val input = testResourcesDir / "input.txt"

        createDirectories()

        if (build.notExists())
            build.writeText(Templates.generateBuildGradle())

        source.writeText(Templates.generateDay(year, dayPrefixed))
        test.writeText(Templates.generateDayTest(year, dayPrefixed))
        example.writeText("")
        input.writeText(InputFetcher(year, day).execute())
    }
}