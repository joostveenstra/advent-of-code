package year2024

import framework.Context
import framework.Day

class Day19(context: Context) : Day by context {
    val towels = lines.first().split(", ")
    val designs = lines.drop(2)

    fun String.isPossible(): Boolean = when {
        isEmpty() -> true
        else -> towels.any { t -> startsWith(t) && drop(t.length).isPossible() }
    }

    fun part1() = designs.count { it.isPossible() }

    val cache = mutableMapOf<String, Long>()
    fun String.countPossible(): Long = cache.getOrPut(this) {
        when {
            isEmpty() -> 1
            else -> towels.sumOf { t -> if (startsWith(t)) drop(t.length).countPossible() else 0 }
        }
    }

    fun part2() = designs.sumOf { it.countPossible() }
}