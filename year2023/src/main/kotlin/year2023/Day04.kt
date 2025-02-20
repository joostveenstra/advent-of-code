package year2023

import framework.Context
import framework.Day
import util.allInts

class Day04(context: Context) : Day by context {
    val cards = lines.map { line ->
        val (winning, yours) = line.split(':').last().split('|').map { it.allInts().toSet() }
        yours.count { it in winning }
    }

    fun part1() = cards.sumOf { wins -> 1 shl wins shr 1 }
    fun part2() = run {
        val count = IntArray(cards.size) { 1 }
        cards.forEachIndexed { index, wins ->
            (1..wins).forEach {
                count[index + it] += count[index]
            }
        }
        count.sum()
    }
}