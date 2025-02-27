package year2018

import framework.Context
import framework.Day
import util.frequencies
import util.pairSequence

class Day02(context: Context) : Day by context {
    fun part1() = with(lines) {
        val occurrences = map { line -> line.frequencies().values.toSet() }
        occurrences.count { 2 in it } * occurrences.count { 3 in it }
    }

    fun part2() = with(lines) {
        val size = first().length
        pairSequence()
            .map { (a, b) -> (a zip b).mapNotNull { (a, b) -> if (a == b) a else null } }
            .first { size - it.size == 1 }
            .joinToString("")
    }
}