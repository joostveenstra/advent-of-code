package year2025

import framework.Context
import framework.Day
import util.allLongs
import util.pow

class Day02(context: Context) : Day by context {
    val first = listOf(2 to 1, 4 to 2, 6 to 3, 8 to 4, 10 to 5)
    val second = listOf(3 to 1, 5 to 1, 6 to 2, 7 to 1, 9 to 3, 10 to 2)
    val third = listOf(6 to 1, 10 to 1)
    val ranges = input.allLongs().chunked(2).toList()

    fun List<Pair<Int, Int>>.sumInvalid() = sumOf { (digits, size) ->
        val digitsPower = 10L.pow(digits)
        val sizePower = 10L.pow(size)
        val step = (digitsPower - 1) / (sizePower - 1)
        val start = step * (sizePower / 10)
        val end = step * (sizePower - 1)

        ranges.sumOf { (from, to) ->
            val firstMultiple = ((from - 1) / step + 1) * step
            val lower = maxOf(firstMultiple, start)
            val upper = minOf(to, end)

            if (lower <= upper) {
                val n = (upper - lower) / step
                val triangular = n * (n + 1) / 2
                lower * (n + 1) + step * triangular
            } else 0
        }
    }

    fun part1() = first.sumInvalid()
    fun part2() = first.sumInvalid() + second.sumInvalid() - third.sumInvalid()
}