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
                assertEquals(value, part(input))
            }
        }

    @TestFactory
    fun part1() = test.part1?.createTests(day::part1) ?: listOf()

    @TestFactory
    fun part2() = test.part2?.createTests(day::part2) ?: listOf()
}