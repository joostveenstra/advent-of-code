package year2022

import framework.Context
import framework.Day
import util.allDistinct

class Day06(context: Context) : Day by context {
    fun String.findMarker(size: Int) = asSequence().windowed(size).indexOfFirst { it.allDistinct() } + size

    fun part1() = input.findMarker(4)
    fun part2() = input.findMarker(14)
}