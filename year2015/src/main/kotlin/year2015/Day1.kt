package year2015

import framework.Day

object Day1 : Day {
    private fun Char.direction() = if (this == '(') 1 else -1

    override fun part1(input: String) = input.fold(0) { floor, char -> floor + char.direction() }

    override fun part2(input: String) = input.scan(0) { floor, char -> floor + char.direction() }.indexOf(-1)
}