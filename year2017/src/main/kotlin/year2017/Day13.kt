package year2017

import framework.Context
import framework.Day
import util.allInts
import util.toPair

class Day13(context: Context) : Day by context {
    val firewall = lines.map { it.allInts().toList().toPair() }

    private fun List<Pair<Int, Int>>.severity(delay: Int) = mapNotNull { (depth, range) ->
        val caught = (depth + delay) % (2 * (range - 1)) == 0
        if (caught) depth * range else null
    }

    fun part1() = firewall.severity(0).sum()
    fun part2() = generateSequence(0) { it + 1 }.indexOfFirst { firewall.severity(it).isEmpty() }
}