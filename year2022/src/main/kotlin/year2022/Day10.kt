package year2022

import framework.Day
import kotlin.math.absoluteValue

object Day10 : Day {
    private fun String.toSignalStrengths() = split("\\s+".toRegex()).map { it.toIntOrNull() ?: 0 }.scan(1, Int::plus)

    override fun part1(input: String) = input.toSignalStrengths().let { signalStrengths ->
        (20..220 step 40).sumOf { cycle -> cycle * signalStrengths[cycle - 1] }
    }

    override fun part2(input: String) = input.toSignalStrengths().dropLast(1).chunked(40).joinToString("\n") { row ->
        row.mapIndexed { pixel, signal -> if ((pixel - signal).absoluteValue <= 1) '#' else '.' }.joinToString("")
    }
}