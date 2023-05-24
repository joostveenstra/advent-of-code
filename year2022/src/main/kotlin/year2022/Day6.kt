package year2022

import framework.Day

object Day6: Day<Int> {
    private fun String.findMarker(size: Int) = asSequence().windowed(size).indexOfFirst { it.distinct().size == size } + size

    override fun part1(input: String) = input.findMarker(4)

    override fun part2(input: String) = input.findMarker(14)
}