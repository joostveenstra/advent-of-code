object Day20 : Day<UInt> {
    private fun String.toRanges() = lines().map {
        it.split('-').let { (first, last) -> first.toUInt()..last.toUInt() }
    }

    private val UIntRange.size get() = last - first + 1u

    private infix fun UIntRange.subtract(other: UIntRange) = when {
        first >= other.first && last <= other.last -> listOf()
        first < other.first && last > other.last -> listOf(first..other.first - 1u, other.last + 1u..last)
        first < other.first && last >= other.first -> listOf(first..other.first - 1u)
        first <= other.last && last > other.last -> listOf(other.last + 1u..last)
        else -> listOf(this)
    }

    private fun List<UIntRange>.intersect() = fold(listOf(0u..UInt.MAX_VALUE)) { remaining, range -> remaining.flatMap { it subtract range } }

    override fun part1(input: String) = input.toRanges().intersect().minOf { it.first }

    override fun part2(input: String) = input.toRanges().intersect().sumOf { it.size }
}