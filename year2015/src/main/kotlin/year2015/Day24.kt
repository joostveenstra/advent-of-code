package year2015

import framework.Context
import framework.Day
import util.combinationSequence
import util.product

class Day24(context: Context) : Day by context {
    val packages = lines.map { it.toLong() }

    fun List<Long>.balance(groups: Int): Long {
        val weight = sum() / groups
        return generateSequence(1) { it + 1 }.flatMap { combinationSequence(it) }.filter { it.sum() == weight }.first().product()
    }

    fun part1() = packages.balance(3)
    fun part2() = packages.balance(4)
}