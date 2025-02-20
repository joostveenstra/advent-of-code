package year2022

import framework.Context
import framework.Day
import util.EMPTY_LINE

class Day01(context: Context) : Day by context {
    val calories = input.split(EMPTY_LINE).map { it.lines().sumOf(String::toInt) }.sortedDescending()

    fun part1() = calories.first()
    fun part2() = calories.take(3).sum()
}