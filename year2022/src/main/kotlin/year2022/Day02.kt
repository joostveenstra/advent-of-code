package year2022

import framework.Context
import framework.Day
import util.zipWithIndex

class Day02(context: Context) : Day by context {
    fun part1(): Int {
        val scores = listOf("", "B X", "C Y", "A Z", "A X", "B Y", "C Z", "C X", "A Y", "B Z").zipWithIndex().toMap()
        return lines.sumOf { scores.getValue(it) }
    }

    fun part2(): Int {
        val scores = listOf("", "B X", "C X", "A X", "A Y", "B Y", "C Y", "C Z", "A Z", "B Z").zipWithIndex().toMap()
        return lines.sumOf { scores.getValue(it) }
    }
}