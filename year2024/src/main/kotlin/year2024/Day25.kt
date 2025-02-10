package year2024

import framework.Day

object Day25 : Day {
    private fun parse(input: String): Pair<List<IntArray>, List<IntArray>> {
        val locks = mutableListOf<IntArray>()
        val keys = mutableListOf<IntArray>()
        input.lines().chunked(8).forEach { schematic ->
            val pins = (0..4).map { col ->
                (1..5).count { row ->
                    schematic[row][col] == '#'
                }
            }.toIntArray()
            if (schematic[0][0] == '#') locks += pins else keys += pins
        }
        return locks to keys
    }

    private infix fun IntArray.fits(lock: IntArray) = indices.all { this[it] + lock[it] <= 5 }

    override fun part1(input: String) = parse(input).let { (keys, locks) ->
        locks.sumOf { l ->
            keys.count { k -> k fits l }
        }
    }

    override fun part2(input: String) = 0
}