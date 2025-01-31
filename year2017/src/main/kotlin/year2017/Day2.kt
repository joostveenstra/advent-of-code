package year2017

import framework.Day
import util.allInts
import util.pairs

object Day2 : Day {
    override fun part1(input: String) = input.lines().map { it.allInts() }.sumOf { it.max() - it.min() }

    override fun part2(input: String) = input.lines().map { it.allInts().toList() }.sumOf { row ->
        row.pairs().firstNotNullOf { (a, b) ->
            val max = maxOf(a, b)
            val min = minOf(a, b)
            if (max % min == 0) max / min else null
        }
    }
}