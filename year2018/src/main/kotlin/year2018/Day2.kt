package year2018

import framework.Day
import util.combinations

object Day2 : Day<Any> {
    override fun part1(input: String) = input.lines().run {
        val occurrences = map { line -> line.groupingBy { it }.eachCount().values.toSet() }
        occurrences.count { 2 in it } * occurrences.count { 3 in it }
    }

    override fun part2(input: String) = input.lines().run {
        val size = first().length
        combinations(2)
            .map { (a, b) -> (a zip b).mapNotNull { (a, b) -> if (a == b) a else null } }
            .first { size - it.size == 1 }
            .joinToString("")
    }
}