package year2017

import framework.Context
import framework.Day
import kotlin.math.absoluteValue

class Day11(context: Context) : Day by context {
    data class Hex(val q: Int, val r: Int) {
        val s: Int get() = -q - r
        fun distance() = maxOf(q.absoluteValue, r.absoluteValue, s.absoluteValue)
        fun step(direction: String) = when (direction) {
            "n" -> Hex(q + 1, r - 1)
            "s" -> Hex(q - 1, r + 1)
            "ne" -> Hex(q + 1, r)
            "sw" -> Hex(q - 1, r)
            "se" -> Hex(q, r + 1)
            else -> Hex(q, r - 1)
        }
    }

    val path = input.split(',').scan(Hex(0, 0), Hex::step).map { it.distance() }

    fun part1() = path.last()
    fun part2() = path.max()
}