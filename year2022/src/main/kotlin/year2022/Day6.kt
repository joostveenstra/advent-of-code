package year2022

import framework.Day
import util.allDistinct

object Day6: Day {
    private fun String.findMarker(size: Int) = asSequence().windowed(size).indexOfFirst { it.allDistinct() } + size

    override fun part1(input: String) = input.findMarker(4)

    override fun part2(input: String) = input.findMarker(14)
}