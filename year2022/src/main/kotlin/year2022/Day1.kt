package year2022

import framework.Day
import util.EMPTY_LINE

object Day1 : Day<Int> {
    private fun String.toCalories() = split(EMPTY_LINE).map { it.lines().sumOf(String::toInt) }

    override fun part1(input: String) = input.toCalories().max()

    override fun part2(input: String) = input.toCalories().sortedDescending().take(3).sum()
}