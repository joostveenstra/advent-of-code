package year2022

import framework.Day

object Day25 : Day {
    private fun String.fromSnafu(): Long =
        fold(0L) { total, char ->
            (5 * total) + when (char) {
                '=' -> -2
                '-' -> -1
                else -> char.digitToInt()
            }
        }

    private fun Long.toSnafu(): String =
        if (this == 0L) ""
        else ((this + 2) / 5).toSnafu() + "012=-"[(this % 5).toInt()]

    override fun part1(input: String) = input.lines().sumOf { it.fromSnafu() }.toSnafu()

    override fun part2(input: String) = ""
}