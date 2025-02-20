package year2024

import framework.Context
import framework.Day

class Day25(context: Context) : Day by context {
    val locks = mutableListOf<IntArray>()
    val keys = mutableListOf<IntArray>()

    init {
        lines.chunked(8).forEach { schematic ->
            val pins = (0..4).map { col ->
                (1..5).count { row ->
                    schematic[row][col] == '#'
                }
            }.toIntArray()
            if (schematic[0][0] == '#') locks += pins else keys += pins
        }
    }

    infix fun IntArray.fits(lock: IntArray) = indices.all { this[it] + lock[it] <= 5 }

    fun part1() = locks.sumOf { l ->
        keys.count { k -> k fits l }
    }
}