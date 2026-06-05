package year2018

import framework.Context
import framework.Day

class Day01(context: Context) : Day by context {
    val changes = lines.map { it.toInt() }

    fun part1() = changes.sum()
    fun part2(): Int {
        val seen = mutableSetOf(0)

        tailrec fun find(index: Int, frequency: Int): Int {
            val next = frequency + changes[index % changes.size]
            return if (!seen.add(next)) next
            else find(index + 1, next)
        }

        return find(0, 0)
    }
}