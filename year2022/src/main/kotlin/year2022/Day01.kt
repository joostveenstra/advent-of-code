package year2022

import framework.Context
import framework.Day
import util.EMPTY_LINE

class Day01(context: Context) : Day by context {
    val calories = input.split(EMPTY_LINE).map { it.lines().sumOf(String::toInt) }

    fun part1() = calories.max()
    fun part2() = calories.sortedDescending().take(3).sum()
}