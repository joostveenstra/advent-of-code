package year2022

import framework.Context
import framework.Day
import kotlin.math.absoluteValue

class Day10(context: Context) : Day by context {
    val signalStrengths = input.split("\\s+".toRegex()).map { it.toIntOrNull() ?: 0 }.scan(1, Int::plus)

    fun part1() = (20..220 step 40).sumOf { cycle -> cycle * signalStrengths[cycle - 1] }
    fun part2() = signalStrengths.dropLast(1).chunked(40).joinToString("\n") { row ->
        row.mapIndexed { pixel, signal -> if ((pixel - signal).absoluteValue <= 1) '#' else '.' }.joinToString("")
    }
}