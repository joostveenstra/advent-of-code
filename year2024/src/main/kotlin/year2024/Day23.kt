package year2024

import framework.Day
import framework.Context
import util.pairs

class Day23(context: Context) : Day by context {
    val nodes = lines
        .map { it.split("-") }
        .flatMap { (from, to) -> listOf(from to to, to to from) }
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.toSet() }

    fun part1() = run {
        val seen = mutableSetOf<String>()
        nodes.keys.filter { it.startsWith('t') }.sumOf { node ->
            seen += node
            nodes.getValue(node).pairs().count { (a, b) -> a !in seen && b !in seen && b in nodes.getValue(a) }
        }
    }

    fun part2() = run {
        val seen = mutableSetOf<String>()
        nodes.mapNotNull { (node, neighbours) ->
            if (node !in seen) {
                val clique = mutableListOf(node)
                neighbours.forEach { n ->
                    if (clique.all { it in nodes.getValue(n) }) {
                        clique += n
                        seen += n
                    }
                }
                clique
            } else null
        }.maxBy { it.size }.sorted().joinToString(",")
    }
}