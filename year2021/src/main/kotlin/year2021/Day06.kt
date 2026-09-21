package year2021

import framework.Context
import framework.Day
import util.allInts

class Day06(context: Context) : Day by context {
    val fish = input.allInts().toList()

    fun simulate(days: Int) = LongArray(9).apply {
        fish.forEach { i -> this[i] += 1 }
        (0..<days).forEach { day -> this[(day + 7) % 9] += this[day % 9] }
    }.sum()

    fun part1() = simulate(80)
    fun part2() = simulate(256)
}