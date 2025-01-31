package year2015

import framework.Day

object Day20 : Day {
    override fun part1(input: String): Int {
        val target = input.toInt() / 10
        val houses = IntArray(target) { 1 }
        for (i in 2 until target)
            for (j in i until target step i)
                houses[j] += i
        return houses.indexOfFirst { it >= target }
    }

    override fun part2(input: String): Int {
        val target = input.toInt() / 11
        val houses = IntArray(target) { 1 }
        for (i in 2 until target)
            for (j in i until minOf(50 * i, target) step i)
                houses[j] += i
        return houses.indexOfFirst { it >= target }
    }
}