package framework

import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.test.assertEquals

@DisplayNameGeneration(FullyQualified::class)
abstract class Test(init: TestSpecBuilder.() -> Unit) {
    private val test = test(init)
    private val dayNum = javaClass.name.substringAfter("Day").substringBefore("Test")
    private val klass = Class.forName("${javaClass.packageName}.Day${dayNum}").kotlin

    private fun Any.notNullProperties() = this::class.memberProperties.mapNotNull {
        it.getter.call(this)?.let { value -> it.name to value }
    }.toMap()

    private fun TestSpec.createTests(): List<DynamicTest> {
        val parts = notNullProperties().mapValues { it.value.notNullProperties() }
        val files = parts.values.flatMap { it.keys }.toSet()
        val functions = parts.mapValues { part ->
            klass.functions.find { it.name == part.key }
        }

        return files.map { name ->
            DynamicTest.dynamicTest(name) {
                val input = resourceAsText("/day${dayNum}/${name}.txt")
                val context = Context(input, name.startsWith("example"))
                val day = klass.primaryConstructor?.call(context)

                parts.entries.forEach { (key, values) ->
                    val part = functions.getValue(key)
                    values[name]?.let { value ->
                        assertEquals(value.toString(), part?.call(day).toString(), key)
                    }
                }
            }
        }
    }

    @TestFactory
    fun test() = test.createTests()
}