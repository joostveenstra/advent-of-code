package year2018

import framework.Context
import framework.Day

class Day01(context: Context) : Day by context {
    val changes = lines.map { it.toInt() }

    fun part1() = changes.sum()
    fun part2(): Int {
        val seen = mutableSetOf(0)
        
        tailrec fun find(index: Int, frequency: Int): Int =
            when (val nextFrequency = frequency + changes[index % changes.size]) {
                in seen -> nextFrequency
                else -> {
                    seen.add(nextFrequency)
                    find(index + 1, nextFrequency)
                }
            }
        
        return find(0, 0)
    }
}