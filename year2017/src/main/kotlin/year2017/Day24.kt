package year2017

import framework.Context
import framework.Day

class Day24(context: Context) : Day by context {
    data class Component(val a: Int, val b: Int, val strength: Int) {
        fun matches(n: Int) = n == a || n == b
        fun opposite(n: Int) = if (n == a) b else a
    }

    val components = lines.map { line ->
        val (a, b) = line.split('/').map { it.toInt() }
        Component(a, b, a + b)
    }.toSet()

    fun buildBridge(comparator: Comparator<Pair<Int, Int>>): Int {
        fun build(components: Set<Component>, current: Int, size: Int, total: Int): Pair<Int, Int> {
            val candidates = components.filter { it.matches(current) }
            val shortlist = candidates.find { it.a == it.b }?.let { listOf(it) } ?: candidates
            return if (shortlist.isEmpty()) size to total
            else shortlist
                .map { next -> build(components - next, next.opposite(current), size + 1, total + next.strength) }
                .maxWith(comparator)
        }
        return build(components, 0, 0, 0).second
    }

    fun part1() = buildBridge(compareBy { it.second })
    fun part2() = buildBridge(compareBy<Pair<Int, Int>> { it.first }.thenBy { it.second })
}