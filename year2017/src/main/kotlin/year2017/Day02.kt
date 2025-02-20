package year2017

import framework.Context
import framework.Day
import util.allInts
import util.pairs

class Day02(context: Context) : Day by context {
    val numbers = lines.map { it.allInts().toList() }

    fun part1() = numbers.sumOf { it.max() - it.min() }
    fun part2() = numbers.sumOf { row ->
        row.pairs().firstNotNullOf { (a, b) ->
            val max = maxOf(a, b)
            val min = minOf(a, b)
            if (max % min == 0) max / min else null
        }
    }
}