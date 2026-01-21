package year2015

import framework.Context
import framework.Day

class Day20(context: Context) : Day by context {
    fun part1() = run {
        val target = input.toInt() / 10
        val houses = IntArray(target) { 1 }
        for (i in 2..<target)
            for (j in i..<target step i)
                houses[j] += i
        houses.indexOfFirst { it >= target }
    }

    fun part2() = run {
        val target = input.toInt() / 11
        val houses = IntArray(target) { 1 }
        for (i in 2..<target)
            for (j in i..<minOf(50 * i, target) step i)
                houses[j] += i
        houses.indexOfFirst { it >= target }
    }
}