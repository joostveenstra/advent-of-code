package year2017

import framework.Day
import util.allInts

object Day13 : Day<Int> {
    private fun String.toFirewall() = lines().map { line ->
        line.allInts().toList().let { (depth, range) -> depth to range }
    }

    private fun List<Pair<Int, Int>>.severity(delay: Int) = mapNotNull { (depth, range) ->
        val caught = (depth + delay) % (2 * (range - 1)) == 0
        if (caught) depth * range else null
    }

    override fun part1(input: String) = input.toFirewall().severity(0).sum()

    override fun part2(input: String): Int {
        val firewall = input.toFirewall()
        return generateSequence(0) { it + 1 }.indexOfFirst { firewall.severity(it).isEmpty() }
    }
}