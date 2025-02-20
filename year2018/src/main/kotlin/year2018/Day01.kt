package year2018

import framework.Context
import framework.Day
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentHashSetOf
import kotlinx.collections.immutable.plus

class Day01(context: Context) : Day by context {
    val changes = lines.map { it.toInt() }

    fun part1() = changes.sum()
    fun part2(): Int {
        tailrec fun find(index: Int, frequency: Int, seen: PersistentSet<Int>): Int =
            when (val nextFrequency = frequency + changes[index % changes.size]) {
                in seen -> nextFrequency
                else -> find(index + 1, nextFrequency, seen + nextFrequency)
            }
        return find(0, 0, persistentHashSetOf(0))
    }
}