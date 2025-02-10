package year2024

import framework.Day
import util.allLongs
import util.log10
import util.pow

object Day11 : Day {
    private fun Sequence<Long>.blink(blinks: Int): Long {
        val cache = mutableMapOf<Pair<Long, Int>, Long>()

        fun countStones(stone: Long, blinks: Int): Long = cache.getOrPut(stone to blinks) {
            when {
                blinks == 0 -> 1
                stone == 0L -> countStones(1, blinks - 1)
                else -> {
                    val digits = log10(stone) + 1
                    if (digits % 2 == 0L) {
                        val power = 10L.pow(digits / 2)
                        countStones(stone / power, blinks - 1) + countStones(stone % power, blinks - 1)
                    } else {
                        countStones(stone * 2024, blinks - 1)
                    }
                }
            }
        }

        return sumOf { stone -> countStones(stone, blinks) }
    }

    override fun part1(input: String) = input.allLongs().blink(25)

    override fun part2(input: String) = input.allLongs().blink(75)
}