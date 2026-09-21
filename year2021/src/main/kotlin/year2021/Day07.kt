package year2021

import framework.Context
import framework.Day
import util.allInts
import util.midpoint
import kotlin.math.absoluteValue

class Day07(context: Context) : Day by context {
    val crabs = input.allInts().toList()
    val median = crabs.sorted().midpoint()
    val mean = crabs.sum() / crabs.size

    fun List<Int>.fuel(target: Int) = sumOf {
        val n = (it - target).absoluteValue
        n * (n + 1) / 2
    }

    fun part1() = crabs.sumOf { (it - median).absoluteValue }
    fun part2() = minOf(crabs.fuel(mean), crabs.fuel(mean + 1))
}