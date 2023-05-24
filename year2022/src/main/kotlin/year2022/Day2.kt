package year2022

import framework.Day
import util.zipWithIndex

object Day2 : Day<Int> {
    override fun part1(input: String): Int {
        val scores = listOf("", "B X", "C Y", "A Z", "A X", "B Y", "C Z", "C X", "A Y", "B Z").zipWithIndex().toMap()
        return input.lines().sumOf { scores.getValue(it) }
    }

    override fun part2(input: String): Int {
        val scores = listOf("", "B X", "C X", "A X", "A Y", "B Y", "C Y", "C Z", "A Z", "B Z").zipWithIndex().toMap()
        return input.lines().sumOf { scores.getValue(it) }
    }
}