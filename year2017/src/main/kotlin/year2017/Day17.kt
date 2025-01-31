package year2017

import framework.Day

object Day17 : Day {
    override fun part1(input: String): Int {
        val steps = input.toInt()
        val buffer = mutableListOf(0)
        var index = 0

        for (i in 1..2017) {
            index = (index + steps + 1) % i
            buffer.add(index, i)
        }

        return buffer[index + 1]
    }

    override fun part2(input: String): Int {
        val steps = input.toInt()
        var index = 0
        var result = -1

        for (i in 1..50000000) {
            index = (index + steps + 1) % i
            if (index == 0) result = i
        }

        return result
    }
}