package year2015

import framework.Context
import framework.Day

class Day01(context: Context) : Day by context {
    fun Char.direction() = if (this == '(') 1 else -1

    fun part1() = input.sumOf { it.direction() }
    fun part2() = input.scan(0) { floor, c -> floor + c.direction() }.indexOf(-1)
}