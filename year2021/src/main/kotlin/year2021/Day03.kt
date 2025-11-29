package year2021

import framework.Context
import framework.Day
import util.toInt

class Day03(context: Context) : Day by context {
    fun List<String>.ones(bit: Int) = count { it[bit] == '1' }

    fun part1() = with(lines) {
        val width = first().length
        val gamma = (0..<width).fold(0) { gamma, i ->
            val ones = ones(i)
            val zeros = size - ones
            gamma shl 1 or (ones > zeros).toInt()
        }
        val epsilon = gamma.inv() and ((1 shl width) - 1)
        gamma * epsilon
    }

    fun List<String>.rating(compare: (Int, Int) -> Boolean): Int {
        tailrec fun List<String>.findNumber(column: Int = 0): String =
            if (size == 1) first()
            else {
                val ones = ones(column)
                val zeros = size - ones
                val bit = if (compare(ones, zeros)) '1' else '0'
                filter { it[column] == bit }.findNumber(column + 1)
            }
        return findNumber().fold(0) { rating, bit -> rating shl 1 or bit.digitToInt() }
    }

    fun part2() = with(lines) {
        val oxygen = rating { ones, zeros -> ones >= zeros }
        val co2 = rating { ones, zeros -> ones < zeros }
        oxygen * co2
    }
}