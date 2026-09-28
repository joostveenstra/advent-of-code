package year2021

import framework.Context
import framework.Day
import util.frequencies

class Day14(context: Context) : Day by context {
    data class Rule(val from: Pair<Char, Char>, val left: Pair<Char, Char>, val right: Pair<Char, Char>, val to: Char)

    val template = lines.first()
    val elements = template.frequencies().mapValues { it.value.toLong() }
    val pairs = template.zipWithNext { a, b -> a to b }.frequencies().mapValues { it.value.toLong() }
    val rules = lines.drop(2).map { l ->
        val (a, b, c) = l.filter { it.isUpperCase() }.toList()
        Rule(Pair(a, b), Pair(a, c), Pair(c, b), c)
    }

    fun steps(steps: Int): Long {
        val elements = elements.toMutableMap()
        (0..<steps).fold(pairs) { acc, _ -> step(elements, acc) }
        return elements.values.let { it.max() - it.min() }
    }

    fun step(elements: MutableMap<Char, Long>, pairs: Map<Pair<Char, Char>, Long>) = buildMap {
        rules.forEach { (from, left, right, to) ->
            val n = pairs.getOrDefault(from, 0)
            merge(left, n, Long::plus)
            merge(right, n, Long::plus)
            elements.merge(to, n, Long::plus)
        }
    }

    fun part1() = steps(10)
    fun part2() = steps(40)
}