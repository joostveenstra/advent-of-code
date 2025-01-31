package year2015

import framework.Day
import util.permutationSequence

object Day9 : Day {
    private fun String.toEdges() = lines().associate { it.split(" ").let { (a, _, b, _, distance) -> setOf(a, b) to distance.toInt() } }

    private fun Map<Set<String>, Int>.travelingSalesman(): Sequence<Int> {
        val nodes = keys.flatten().distinct()
        return nodes.permutationSequence().map { p ->
            p.windowed(2).map { it.toSet() }.sumOf { getValue(it) }
        }
    }

    override fun part1(input: String) = input.toEdges().travelingSalesman().min()

    override fun part2(input: String) = input.toEdges().travelingSalesman().max()
}