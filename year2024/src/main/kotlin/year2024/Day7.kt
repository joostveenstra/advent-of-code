package year2024

import framework.Day
import util.allLongs
import util.log10
import util.pow

object Day7 : Day {
    private fun String.toEquations() = lines().map { line ->
        line.allLongs().toList().let { it.first() to it.drop(1) }
    }

    private fun Long.nextPowerOfTen() = 10L.pow(log10(this) + 1)

    private fun List<Pair<Long, List<Long>>>.calibrate(concat: Boolean): Long {
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

        return filter { (lhs, terms) -> terms.isEqualTo(lhs) }.sumOf { it.first }
    }

    override fun part1(input: String) = input.toEquations().calibrate(false)

    override fun part2(input: String) = input.toEquations().calibrate(true)
}