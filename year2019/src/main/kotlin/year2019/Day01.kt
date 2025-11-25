package year2019

import framework.Context
import framework.Day
import util.allInts

class Day01(context: Context) : Day by context {
    val mass = input.allInts().toList()
    
    fun fuel(mass: Int) = mass / 3 - 2

    fun part1() = mass.sumOf(::fuel)
    fun part2() = mass.sumOf { m ->
        generateSequence(m, ::fuel).drop(1).takeWhile { it > 0 }.sum()
    }
}