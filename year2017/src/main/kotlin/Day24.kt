object Day24 : Day<Int> {
    data class Component(val a: Int, val b: Int, val strength: Int) {
        fun matches(n: Int) = n == a || n == b
        fun opposite(n: Int) = if (n == a) b else a
    }

    private fun String.toComponents() = lines().map { line ->
        val (a, b) = line.split('/').map { it.toInt() }
        Component(a, b, a + b)
    }.toSet()

    private fun buildBridge(input: String, comparator: Comparator<Pair<Int, Int>>): Int {
        fun build(components: Set<Component>, current: Int, size: Int, total: Int): Pair<Int, Int> {
            val candidates = components.filter { it.matches(current) }
            val shortlist = candidates.find { it.a == it.b }?.let { listOf(it) } ?: candidates
            return if (shortlist.isEmpty()) size to total
            else shortlist
                .map { next -> build(components - next, next.opposite(current), size + 1, total + next.strength) }
                .maxWith(comparator)
        }
        return build(input.toComponents(), 0, 0, 0).second
    }

    override fun part1(input: String) = buildBridge(input, compareBy { it.second })

    override fun part2(input: String) = buildBridge(input, compareBy<Pair<Int, Int>> { it.first }.thenBy { it.second })
}