package year2018

import framework.Day
import util.nth

object Day12 : Day {
    private fun String.toPlantsRules() = lines().run {
        val plants = first().asSequence().drop(15).withIndex().filter { it.value == '#' }.map { it.index }.toSet()
        val rules = drop(2).filter { it.last() == '#' }.map { it.take(5) }.toSet()
        plants to rules
    }

    private fun Set<Int>.step(rules: Set<String>) = (min() - 2..max() + 2).mapNotNull { index ->
        val pattern = (-2..2).fold("") { pattern, offset ->
            pattern + if ((index + offset) in this) '#' else '.'
        }
        if (pattern in rules) index else null
    }.toSet()

    override fun part1(input: String): Int {
        val (plants, rules) = input.toPlantsRules()
        return generateSequence(plants) { it.step(rules) }.nth(20).sum()
    }

    override fun part2(input: String): Long {
        val (plants, rules) = input.toPlantsRules()

        tailrec fun Set<Int>.lastSum(previousPreviousDelta: Int, previousDelta: Int, generation: Int): Long {
            val next = step(rules)
            val delta = next.sum() - sum()
            return if (delta == previousDelta && delta == previousPreviousDelta) sum() + delta * (50000000000L - generation)
            else next.lastSum(previousDelta, delta, generation + 1)
        }

        return plants.lastSum(-1, 0, 0)
    }
}