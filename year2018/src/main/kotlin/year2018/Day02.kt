package year2018

import framework.Context
import framework.Day
import util.frequencies

class Day02(context: Context) : Day by context {
    fun part1() = with(lines) {
        val occurrences = map { line -> line.frequencies().values.toSet() }
        occurrences.count { 2 in it } * occurrences.count { 3 in it }
    }
    
    fun part2() = with(lines) {
        val seen = mutableSetOf<Pair<String, String>>()
        val size = first().length
        (0..<size).flatMap { column ->
            map { id ->
                val prefix = id.substring(0, column)
                val suffix = id.substring(column + 1, size)
                prefix to suffix
            }
        }.first { !seen.add(it) }.let { (a, b) -> a + b }
    }
}