package year2019

import framework.Context
import framework.Day

class Day01(context: Context) : Day by context {
    fun fuel(mass: Int) = mass / 3 - 2

    fun part1() = lines.sumOf { fuel(it.toInt()) }
    fun part2() = lines.sumOf { line ->
        generateSequence(line.toInt(), ::fuel).drop(1).takeWhile { it > 0 }.sum()
    }
}