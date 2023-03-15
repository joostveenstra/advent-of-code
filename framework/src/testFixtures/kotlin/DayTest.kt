import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.reflect.full.memberProperties
import kotlin.test.assertEquals

abstract class DayTest(private val day: Day<*>, init: TestSpecBuilder.() -> Unit) {
    private val test = test(init)
    private val dayNum = day.javaClass.name.substringAfter("Day").toInt()

    private val PartSpec.notNullProperties
        get() = PartSpec::class.memberProperties.mapNotNull { it.get(this)?.let { value -> it.name to value } }

    private fun PartSpec.createTests(part: (String) -> Any?): List<DynamicTest> =
        notNullProperties.map { (name, value) ->
            DynamicTest.dynamicTest(name) {
                val input = resourceAsText("/day${dayNum}/${name}.txt")
                assertEquals(part(input), value)
            }
        }

    @TestFactory
    @DisplayName("Part 1")
    fun part1() = test.part1?.createTests(day::part1) ?: emptyList()

    @TestFactory
    @DisplayName("Part 2")
    fun part2() = test.part2?.createTests(day::part2) ?: emptyList()
}