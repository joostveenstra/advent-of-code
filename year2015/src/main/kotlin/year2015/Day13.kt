package year2015

import framework.Day
import util.permutations

object Day13 : Day<Int> {
    private fun String.toPairs() = lines().associate { it.toPair() }.withDefault { 0 }

    private fun String.toPair() = dropLast(1).split(' ').let {
        listOf(it[0], it[10]) to if (it[2] == "gain") it[3].toInt() else -it[3].toInt()
    }

    private fun Map<List<String>, Int>.maximize(transform: (List<String>) -> List<String>): Int {
        val persons = keys.flatten().distinct().let(transform)
        return persons.permutations().maxOf { p ->
            (p + p.first()).windowed(2).sumOf { getValue(it) + getValue(it.asReversed()) }
        }
    }

    override fun part1(input: String) = input.toPairs().maximize { it }

    override fun part2(input: String) = input.toPairs().maximize { it + "me" }
}