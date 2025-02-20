package year2024

import framework.Context
import framework.Day
import util.allLongs
import util.log10
import util.pow

class Day07(context: Context) : Day by context {
    val equations = lines.map { line ->
        line.allLongs().toList().let { it.first() to it.drop(1) }
    }

    fun Long.nextPowerOfTen() = 10L.pow(log10(this) + 1)

    fun calibrate(concat: Boolean): Long {
        fun List<Long>.isEqualTo(lhs: Long): Boolean = when (size) {
            1 -> lhs == first()
            else -> {
                val term = last()
                val next = subList(0, size - 1)
                // @formatter:off
                concat && lhs % term.nextPowerOfTen() == term  && next.isEqualTo(lhs / term.nextPowerOfTen())
                        || (lhs >= term)                       && next.isEqualTo(lhs - term)
                        || (lhs % term == 0L)                  && next.isEqualTo(lhs / term)
                // @formatter:on
            }
        }

        return equations.filter { (lhs, terms) -> terms.isEqualTo(lhs) }.sumOf { it.first }
    }

    fun part1() = calibrate(false)
    fun part2() = calibrate(true)
}