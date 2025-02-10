package year2024

import framework.Day
import util.pairs

object Day23 : Day {
    private fun String.toNodes() = lines()
        .map { it.split("-") }
        .flatMap { (from, to) -> listOf(from to to, to to from) }
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.toSet() }

    override fun part1(input: String) = with(input.toNodes()) {
        val seen = mutableSetOf<String>()
        keys.filter { it.startsWith('t') }.sumOf { node ->
            seen += node
            getValue(node).pairs().count { (a, b) -> a !in seen && b !in seen && b in getValue(a) }
        }
    }

    override fun part2(input: String) = with(input.toNodes()) {
        val seen = mutableSetOf<String>()
        mapNotNull { (node, neighbours) ->
            if (node !in seen) {
                val clique = mutableListOf(node)
                neighbours.forEach { n ->
                    if (clique.all { it in getValue(n) }) {
                        clique += n
                        seen += n
                    }
                }
                clique
            } else null
        }.maxBy { it.size }.sorted().joinToString(",")
    }
}