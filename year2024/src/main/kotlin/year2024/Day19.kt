package year2024

import framework.Day

object Day19 : Day {
    private fun parse(input: String) = input.lines().let { it.first().split(", ") to it.drop(2) }

    override fun part1(input: String): Int {
        val (towels, designs) = parse(input)

        fun String.isPossible(): Boolean = when {
            isEmpty() -> true
            else -> towels.any { t -> startsWith(t) && drop(t.length).isPossible() }
        }

        return designs.count { it.isPossible() }
    }

    override fun part2(input: String): Long {
        val (towels, designs) = parse(input)
        val cache = mutableMapOf<String, Long>()

        fun String.countPossible(): Long = cache.getOrPut(this) {
            when {
                isEmpty() -> 1
                else -> towels.sumOf { t -> if (startsWith(t)) drop(t.length).countPossible() else 0 }
            }
        }

        return designs.sumOf { it.countPossible() }
    }
}