package year2025

import framework.Context
import framework.Day
import util.productOf

class Day11(context: Context) : Day by context {
    val nodes = lines.associate { l ->
        l.split(": ").let { (from, to) -> from to to.split(' ') }
    }

    fun count(from: String, to: String): Long {
        val cache = mutableMapOf<String, Long>()

        fun count(from: String): Long = cache.getOrPut(from) {
            if (from == to) 1L
            else nodes[from]?.sumOf { count(it) } ?: 0
        }

        return count(from)
    }

    fun paths(vararg path: String) = path.asIterable().zipWithNext().productOf { (from, to) -> count(from, to) }

    fun part1() = paths("you", "out")
    fun part2() = paths("svr", "dac", "fft", "out") + paths("svr", "fft", "dac", "out")
}