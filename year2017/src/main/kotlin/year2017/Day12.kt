package year2017

import framework.Day
import util.allInts

object Day12 : Day {
    private fun String.toCliques() = lines().map { it.allInts().toList() }
        .fold(listOf<List<Int>>()) { groups, direct ->
            val (other, linked) = groups.partition { group -> direct.none { it in group } }
            other + listOf(linked.flatten() + direct)
        }

    override fun part1(input: String) = input.toCliques().first { 0 in it }.toSet().size

    override fun part2(input: String) = input.toCliques().size
}