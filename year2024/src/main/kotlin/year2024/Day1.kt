package year2024

import framework.Day
import util.allInts
import util.toPair
import kotlin.math.absoluteValue

object Day1 : Day {
    private fun String.toLists() = allInts().chunked(2).map { it.toPair() }.unzip()

    override fun part1(input: String) = with(input.toLists()) {
        val left = first.sorted()
        val right = second.sorted()
        (left zip right).sumOf { (l, r) -> (r - l).absoluteValue }
    }

    override fun part2(input: String) = with(input.toLists()) {
        val frequencies = second.groupingBy { it }.eachCount()
        first.sumOf { l -> l * frequencies.getOrDefault(l, 0) }
    }
}