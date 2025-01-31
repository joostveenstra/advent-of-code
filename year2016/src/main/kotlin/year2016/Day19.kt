package year2016

import framework.Day
import kotlin.math.floor
import kotlin.math.log
import kotlin.math.pow

object Day19 : Day {
    override fun part1(input: String): Int {
        val elves = input.toInt()
        return ((elves - elves.takeHighestOneBit()) shl 1) + 1
    }

    override fun part2(input: String): Int {
        val elves = input.toInt()
        val maxPower3 = 3.0.pow(floor(log(elves - 1.0, 3.0))).toInt()
        return elves - maxPower3 + maxOf(0, elves - 2 * maxPower3)
    }
}