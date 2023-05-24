package year2017

import framework.Day
import util.allInts
import util.combinations

object Day2 : Day<Int> {
    override fun part1(input: String) = input.lines().map { it.allInts() }.sumOf { it.max() - it.min() }

    override fun part2(input: String) = input.lines().map { it.allInts() }.sumOf { row ->
        row.toList().combinations(2).firstNotNullOf { (a, b) ->
            val max = maxOf(a, b)
            val min = minOf(a, b)
            if (max % min == 0) max / min else null
        }
    }
}