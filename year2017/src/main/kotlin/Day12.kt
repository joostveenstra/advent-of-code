object Day12 : Day<Int> {
    private fun String.toCliques() = lines().map { it.allInts().toSet() }
        .fold(setOf<Set<Int>>()) { groups, direct ->
            val (other, linked) = groups.partition { it.intersect(direct).isEmpty() }
            other.toSet().plusElement(linked.flatten().toSet() + direct)
        }

    override fun part1(input: String) = input.toCliques().first { 0 in it }.size

    override fun part2(input: String) = input.toCliques().size
}