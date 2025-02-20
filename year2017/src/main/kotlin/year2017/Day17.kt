package year2017

import framework.Context
import framework.Day

class Day17(context: Context) : Day by context {
    val steps = input.toInt()

    fun part1(): Int {
        val buffer = mutableListOf(0)
        var index = 0

        for (i in 1..2017) {
            index = (index + steps + 1) % i
            buffer.add(index, i)
        }

        return buffer[index + 1]
    }

    fun part2(): Int {
        var index = 0
        var result = -1

        for (i in 1..50000000) {
            index = (index + steps + 1) % i
            if (index == 0) result = i
        }

        return result
    }
}