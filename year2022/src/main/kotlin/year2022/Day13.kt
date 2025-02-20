package year2022

import framework.Context
import framework.Day
import util.productOf

class Day13(context: Context) : Day by context {
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

    val packets = lines.filter { it.isNotEmpty() }.map { Packet(it.replace("10", "A")) }

    fun part1() = packets.chunked(2).withIndex()
        .filter { (_, pair) -> pair.first() < pair.last() }
        .sumOf { it.index + 1 }

    fun part2(): Int {
        val dividerPackets = listOf("[[2]]", "[[6]]").map(::Packet)
        return (packets + dividerPackets).sorted().withIndex()
            .filter { (_, p) -> p in dividerPackets }
            .productOf { it.index + 1 }
    }
}