package year2024

import framework.Day
import util.allInts

object Day2 : Day {
    private fun String.toReports() = lines().map { it.allInts().toList() }

    private fun List<Int>.isSafe(): Boolean {
        val diffs = zipWithNext { a, b -> b - a }
        return diffs.all { it in 1..3 } || diffs.all { it in -3..-1 }
    }

    override fun part1(input: String) = input.toReports().count { it.isSafe() }

    override fun part2(input: String) = input.toReports().count { report ->
        report.isSafe() || report.indices.any { i ->
            report.toMutableList().apply { removeAt(i) }.isSafe()
        }
    }
}