object Day1 : Day<Int> {
    override fun part1(input: String) = (input + input.first())
        .map { it.digitToInt() }
        .windowed(2)
        .sumOf { (a, b) -> if (a == b) a else 0 }

    override fun part2(input: String) = input
        .map { it.digitToInt() }
        .let {
            it.mapIndexed { i, n -> if (n == it[(i + input.length / 2) % input.length]) n else 0 }
        }
        .sum()
}