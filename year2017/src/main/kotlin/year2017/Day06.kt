package year2017

import framework.Context
import framework.Day
import util.allInts

class Day06(context: Context) : Day by context {
    val banks = input.allInts().toMutableList()
    val seen = mutableMapOf<Int, Int>()

    tailrec fun findLoop(cycles: Int = 0): Int =
        if (banks.hashCode() in seen) cycles
        else {
            seen[banks.hashCode()] = cycles
            val max = banks.max()
            val start = banks.indexOf(max)
            banks[start] = 0
            for (offset in 1..max) {
                val index = (start + offset) % banks.size
                banks[index] += 1
            }
            findLoop(cycles + 1)
        }

    val cycles = findLoop()
    
    fun part1() = cycles
    fun part2() = cycles - seen.getValue(banks.hashCode())
}