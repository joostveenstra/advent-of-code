package year2024

import framework.Context
import framework.Day
import util.allInts

class Day02(context: Context) : Day by context {
    val reports = lines.map { it.allInts().toList() }

    fun List<Int>.isSafe(): Boolean {
        val diffs = zipWithNext { a, b -> b - a }
        return diffs.all { it in 1..3 } || diffs.all { it in -3..-1 }
    }

    fun part1() = reports.count { it.isSafe() }
    fun part2() = reports.count { report ->
        report.isSafe() || report.indices.any { i ->
            report.toMutableList().apply { removeAt(i) }.isSafe()
        }
    }
}