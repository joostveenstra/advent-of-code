package year2015

import framework.Context
import framework.Day
import util.permutationSequence

class Day09(context: Context) : Day by context {
    val edges = lines.associate { it.split(" ").let { (a, _, b, _, distance) -> setOf(a, b) to distance.toInt() } }
    val routes = edges.travelingSalesman()

    fun Map<Set<String>, Int>.travelingSalesman(): Sequence<Int> {
        val nodes = keys.flatten().distinct()
        return nodes.permutationSequence().map { p ->
            p.windowed(2).map { it.toSet() }.sumOf { getValue(it) }
        }
    }

    fun part1() = routes.min()
    fun part2() = routes.max()
}