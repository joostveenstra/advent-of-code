package year2015

import framework.Day
import util.combinationSequence
import util.product

object Day24 : Day {
    private fun String.toPackages() = lines().map { it.toLong() }

    private fun List<Long>.balance(groups: Int): Long {
        val weight = sum() / groups
        return generateSequence(1) { it + 1 }.flatMap { combinationSequence(it) }.filter { it.sum() == weight }.first().product()
    }

    override fun part1(input: String) = input.toPackages().balance(3)

    override fun part2(input: String) = input.toPackages().balance(4)
}