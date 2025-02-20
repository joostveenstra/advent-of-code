package year2022

import framework.Context
import framework.Day

class Day25(context: Context) : Day by context {
    fun String.fromSnafu(): Long =
        fold(0L) { total, char ->
            (5 * total) + when (char) {
                '=' -> -2
                '-' -> -1
                else -> char.digitToInt()
            }
        }

    fun Long.toSnafu(): String =
        if (this == 0L) ""
        else ((this + 2) / 5).toSnafu() + "012=-"[(this % 5).toInt()]

    fun part1() = lines.sumOf { it.fromSnafu() }.toSnafu()
}