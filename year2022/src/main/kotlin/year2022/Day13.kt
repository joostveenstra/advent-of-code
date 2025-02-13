package year2022

import framework.Day
import util.productOf

object Day13 : Day {
    data class Packet(val value: String) : Comparable<Packet> {
        override fun compareTo(other: Packet): Int {
            val left = this.value
            val right = other.value
            val l = left.first()
            val r = right.first()
            return when {
                l == r -> compareValuesBy(left.drop(1), right.drop(1)) { Packet(it) }
                l == ']' -> -1
                r == ']' -> 1
                l == '[' -> compareValuesBy(left.drop(1), "$r]" + right.drop(1)) { Packet(it) }
                r == '[' -> compareValuesBy("$l]" + left.drop(1), right.drop(1)) { Packet(it) }
                else -> l compareTo r
            }
        }
    }

    private fun String.toPackets() = lines().filter { it.isNotEmpty() }.map { Packet(it.replace("10", "A")) }

    override fun part1(input: String) = input.toPackets().chunked(2).withIndex()
        .filter { (_, pair) -> pair.first() < pair.last() }
        .sumOf { it.index + 1 }

    override fun part2(input: String): Int {
        val packets = input.toPackets()
        val dividerPackets = listOf("[[2]]", "[[6]]").map(::Packet)
        return (packets + dividerPackets).sorted().withIndex()
            .filter { (_, p) -> p in dividerPackets }
            .productOf { it.index + 1 }
    }
}