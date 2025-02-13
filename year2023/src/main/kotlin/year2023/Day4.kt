package year2023

import framework.Day
import util.allInts

object Day4 : Day {
    private fun String.toCards() = lines().map { line ->
        val (winning, yours) = line.split(':').last().split('|').map { it.allInts().toSet() }
        yours.count { it in winning }
    }

    override fun part1(input: String) = input.toCards().sumOf { wins -> 1 shl wins shr 1 }

    override fun part2(input: String): Int {
        val cards = input.toCards()
        val count = IntArray(cards.size) { 1 }
        cards.forEachIndexed { index, wins ->
            (1..wins).forEach {
                count[index + it] += count[index]
            }
        }
        return count.sum()
    }
}