@DslMarker
internal annotation class TestMarker

internal data class TestSpec(val part1: PartSpec?, val part2: PartSpec?)
internal data class PartSpec(val example: Any?, val example2: Any?, val input: Any?)

@TestMarker
class TestSpecBuilder {
    private var part1: PartSpec? = null
    private var part2: PartSpec? = null

    fun part1(init: PartSpecBuilder.() -> Unit) {
        part1 = PartSpecBuilder().apply(init).build()
    }

    fun part2(init: PartSpecBuilder.() -> Unit) {
        part2 = PartSpecBuilder().apply(init).build()
    }

    internal fun build() = TestSpec(part1, part2)
}

internal fun test(init: TestSpecBuilder.() -> Unit) = TestSpecBuilder().apply(init).build()

@TestMarker
class PartSpecBuilder {
    var example: Any? = null
    var example2: Any? = null
    var input: Any? = null

    internal fun build() = PartSpec(example, example2, input)
}