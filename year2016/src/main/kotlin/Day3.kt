object Day3 : Day<Int> {
    private fun String.toTriples() = lines().map { line -> line.trim().split(" +".toRegex()).map { it.toInt() } }

    private fun List<Int>.isValidTriangle() = let { (a, b, c) -> a + b > c && b + c > a && c + a > b }

    override fun part1(input: String) = input.toTriples().count { it.isValidTriangle() }

    override fun part2(input: String) = input.toTriples().chunked(3).flatMap { it.transpose() }.count { it.isValidTriangle() }
}