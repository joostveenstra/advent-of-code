package year2018

import framework.Context
import framework.Day
import util.nth

class Day12(context: Context) : Day by context {
    val plants = lines.first().asSequence().drop(15).withIndex().filter { it.value == '#' }.map { it.index }.toSet()
    val rules = lines.drop(2).filter { it.last() == '#' }.map { it.take(5) }.toSet()

    fun Set<Int>.step(rules: Set<String>) = (min() - 2..max() + 2).mapNotNull { index ->
        val pattern = (-2..2).fold("") { pattern, offset ->
            pattern + if ((index + offset) in this) '#' else '.'
        }
        if (pattern in rules) index else null
    }.toSet()

    fun part1() = generateSequence(plants) { it.step(rules) }.nth(20).sum()

    fun part2(): Long {
        tailrec fun Set<Int>.lastSum(previousPreviousDelta: Int, previousDelta: Int, generation: Int): Long {
            val next = step(rules)
            val delta = next.sum() - sum()
            return if (delta == previousDelta && delta == previousPreviousDelta) sum() + delta * (50000000000L - generation)
            else next.lastSum(previousDelta, delta, generation + 1)
        }

        return plants.lastSum(-1, 0, 0)
    }
}