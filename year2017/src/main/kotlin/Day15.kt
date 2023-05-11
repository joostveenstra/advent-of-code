object Day15 : Day<Int> {
    private fun String.toGenerators() = allInts().map { it.toLong() }.toList().let { (a, b) ->
        generator(a, 16807) to generator(b, 48271)
    }

    private fun generator(start: Long, factor: Long) = generateSequence(start) { (it * factor) % 2147483647 }.drop(1)

    private fun judge(a: Long, b: Long) = (a and 0xFFFF) == (b and 0xFFFF)

    override fun part1(input: String) = input.toGenerators().let { (a, b) ->
        (a zip b).take(40000000).count { (a, b) -> judge(a, b) }
    }

    override fun part2(input: String) = input.toGenerators().let { (a, b) ->
        (a.filter { it % 4 == 0L } zip b.filter { it % 8 == 0L }).take(5000000).count { (a, b) -> judge(a, b) }
    }
}