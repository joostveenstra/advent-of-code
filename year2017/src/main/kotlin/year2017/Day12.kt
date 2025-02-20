package year2017

import framework.Context
import framework.Day
import util.allInts

class Day12(context: Context) : Day by context {
    val cliques = lines.map { it.allInts().toList() }
        .fold(listOf<List<Int>>()) { groups, direct ->
            val (other, linked) = groups.partition { group -> direct.none { it in group } }
            other + listOf(linked.flatten() + direct)
        }

    fun part1() = cliques.first { 0 in it }.toSet().size
    fun part2() = cliques.size
}