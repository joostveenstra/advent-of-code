package year2015

import framework.Context
import framework.Day

class Day20(context: Context) : Day by context {
    fun part1() = run {
        val target = this.input.toInt() / 10
        val houses = IntArray(target) { 1 }
        for (i in 2 until target)
            for (j in i until target step i)
                houses[j] += i
        houses.indexOfFirst { it >= target }
    }

    fun part2() = run {
        val target = this.input.toInt() / 11
        val houses = IntArray(target) { 1 }
        for (i in 2 until target)
            for (j in i until minOf(50 * i, target) step i)
                houses[j] += i
        houses.indexOfFirst { it >= target }
    }
}