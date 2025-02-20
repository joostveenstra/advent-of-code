package year2023

import framework.Context
import framework.Day
import util.EMPTY_LINE
import util.allLongs

class Day05(context: Context) : Day by context {
    val mappers = this@Day05.input.split(EMPTY_LINE).drop(1).map { map ->
        map.lines().drop(1)
            .map { line ->
                line.allLongs().toList().let { (dest, start, length) ->
                    listOf(dest, start, start + length)
                }
            }
            .sortedBy { (_, start) -> start }
            .let { NumberMapper(it) }
    }

    val numbers = this@Day05.input.lineSequence().first().allLongs()
    val seeds = numbers.toList()
    val seedRanges = numbers.chunked(2).map { (start, length) -> start to start + length }.toList()

    class NumberMapper(private val maps: List<List<Long>>) {
        fun map(n: Long): Long = maps.find { (_, start, end) -> n in start..<end }?.let { (dest, start) -> n - start + dest } ?: n

        fun map(range: Pair<Long, Long>): List<Pair<Long, Long>> {
            val (remaining, ranges) = maps.fold(range to listOf<Pair<Long, Long>>()) { (range, ranges), (dest, s2, e2) ->
                val (s1, e1) = range
                val x1 = maxOf(s1, s2)
                val x2 = minOf(e1, e2)
                if (x1 < x2) {
                    val remaining = (x2 to e1)
                    val next = (if (s1 < x1) ranges + (s1 to x1) else ranges) + (x1 - s2 + dest to x2 - s2 + dest)
                    remaining to next
                } else {
                    range to ranges
                }
            }
            val (start, end) = remaining
            return if (end - start > 0) ranges + remaining else ranges
        }
    }

    fun part1() = mappers.fold(seeds) { ns, mapper -> ns.map { n -> mapper.map(n) } }.min()
    fun part2() = mappers.fold(seedRanges) { ranges, mapper -> ranges.flatMap { range -> mapper.map(range) } }.minOf { it.first }
}