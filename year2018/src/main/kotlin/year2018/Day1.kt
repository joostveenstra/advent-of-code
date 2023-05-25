package year2018

import framework.Day
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentHashSetOf
import kotlinx.collections.immutable.plus

object Day1 : Day<Int> {
    private fun String.toChanges() = lines().map { it.toInt() }

    override fun part1(input: String) = input.toChanges().sum()

    override fun part2(input: String): Int {
        val changes = input.toChanges()
        tailrec fun find(index: Int, frequency: Int, seen: PersistentSet<Int>): Int =
            when (val nextFrequency = frequency + changes[index % changes.size]) {
                in seen -> nextFrequency
                else -> find(index + 1, nextFrequency, seen + nextFrequency)
            }
        return find(0, 0, persistentHashSetOf(0))
    }
}