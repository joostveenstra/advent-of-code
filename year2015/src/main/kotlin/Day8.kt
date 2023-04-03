object Day8 : Day<Int> {
    override fun part1(input: String) = input.lines().sumOf { line ->
        line.length - line.drop(1).dropLast(1)
            .replace("\\\\", "a")
            .replace("\\\"", "b")
            .replace("\\\\x..".toRegex(), "c")
            .length
    }

    override fun part2(input: String) = input.lines().sumOf { line -> 2 + line.count { it == '\\' || it == '\"' } }
}