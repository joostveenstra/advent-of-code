package year2015

import framework.Context
import framework.Day
import util.permutationSequence

class Day13(context: Context) : Day by context {
    val pairs = lines.associate { l ->
        l.dropLast(1).split(' ').let { listOf(it[0], it[10]) to if (it[2] == "gain") it[3].toInt() else -it[3].toInt() }
    }.withDefault { 0 }

    fun Map<List<String>, Int>.maximize(transform: (List<String>) -> List<String>): Int {
        val persons = keys.flatten().distinct().let(transform)
        return persons.permutationSequence().maxOf { p ->
            (p + p.first()).windowed(2).sumOf { getValue(it) + getValue(it.asReversed()) }
        }
    }

    fun part1() = pairs.maximize { it }
    fun part2() = pairs.maximize { it + "me" }
}