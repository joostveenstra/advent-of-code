object Day14 : Day<Int> {
    private fun String.knotHash(): List<Int> {
        val lengths = map { it.code } + listOf(17, 31, 73, 47, 23)
        val repeated = List(64) { lengths }.flatten()
        val initial = (0..255).toList() to 0
        val (numbers, position) = repeated.zipWithIndex().fold(initial) { (numbers, position), (length, skip) ->
            val next = numbers.take(length).reversed() + numbers.drop(length)
            val offset = (length + skip) and 0xFF
            next.drop(offset) + next.take(offset) to ((position - offset) and 0xFF)
        }
        return (numbers.drop(position) + numbers.take(position)).chunked(16).map { it.reduce(Int::xor) }
    }

    private fun String.toBinary() = (0..127).map { row ->
        "$this-$row".knotHash().joinToString("") { it.toString(2).padStart(8, '0') }
    }

    override fun part1(input: String) = input.toBinary().sumOf { row -> row.count { it == '1' } }

    override fun part2(input: String): Int {
        val used = input.toBinary().withIndex().flatMap { (y, row) ->
            (0..127).mapNotNull { x -> if (row[x] == '1') Point(x, y) else null }
        }
        val cliques = used.fold(listOf<List<Point>>()) { groups, point ->
            val (other, linked) = groups.partition { group -> point.cardinalNeighbours.none { it in group } }
            other + listOf(linked.flatten() + point)
        }
        return cliques.size
    }
}