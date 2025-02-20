package year2017

import framework.Context
import framework.Day

class Day01(context: Context) : Day by context {
    fun part1() = (input + input.first())
        .map { it.digitToInt() }
        .zipWithNext()
        .sumOf { (a, b) -> if (a == b) a else 0 }

    fun part2() = input
        .map { it.digitToInt() }
        .let {
            it.mapIndexed { i, n -> if (n == it[(i + input.length / 2) % input.length]) n else 0 }
        }
        .sum()
}