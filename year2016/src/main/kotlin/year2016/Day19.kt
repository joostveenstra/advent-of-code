package year2016

import framework.Context
import framework.Day
import kotlin.math.floor
import kotlin.math.log
import kotlin.math.pow

class Day19(context: Context) : Day by context {
    val elves = input.toInt()

    fun part1() = ((elves - elves.takeHighestOneBit()) shl 1) + 1

    val maxPower3 = 3.0.pow(floor(log(elves - 1.0, 3.0))).toInt()

    fun part2() = elves - maxPower3 + maxOf(0, elves - 2 * maxPower3)
}