object Day3 : Day<Int> {
    private fun Char.priority() = if (isUpperCase()) this - 'A' + 27 else this - 'a' + 1

    private fun List<String>.commonItem() = map { it.toSet() }.reduce { a, b -> a intersect b }.first()

    override fun part1(input: String) = input.lines()
        .map { it.chunked(it.length / 2) }
        .sumOf { it.commonItem().priority() }

    override fun part2(input: String) = input.lines()
        .chunked(3)
        .sumOf { it.commonItem().priority() }
}