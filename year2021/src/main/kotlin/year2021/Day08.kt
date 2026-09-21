package year2021

import framework.Context
import framework.Day
import util.frequencies

class Day08(context: Context) : Day by context {
    val displays = lines.map { l ->
        val (patterns, output) = l.split(" | ")
        val freq = patterns.frequencies()
        output.split(' ').map { it.sumOf(freq::getValue).toDigit() }
    }

    fun Int.toDigit() = when (this) {
        42 -> 0
        17 -> 1
        34 -> 2
        39 -> 3
        30 -> 4
        37 -> 5
        41 -> 6
        25 -> 7
        49 -> 8
        45 -> 9
        else -> error("Cannot convert $this to digit")
    }

    fun part1() = displays.flatten().count { it in setOf(1, 4, 7, 8) }
    fun part2() = displays.sumOf { (a, b, c, d) -> 1000 * a + 100 * b + 10 * c + d }
}