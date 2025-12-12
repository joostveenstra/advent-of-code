package year2025

import framework.Context
import framework.Day

class Day11(context: Context) : Day by context {
    val nodes = lines.associate { l ->
        l.split(": ").let { (from, to) -> from to to.split(' ') }
    }

    fun paths(from: String, to: String): Long {
        val cache = mutableMapOf<String, Long>()

        fun count(from: String): Long = cache.getOrPut(from) {
            if (from == to) 1L
            else nodes[from]?.sumOf { count(it) } ?: 0
        }

        return count(from)
    }

    fun part1() = paths("you", "out")
    fun part2() = run {
        val path1 = paths("svr", "dac") * paths("dac", "fft") * paths("fft", "out")
        val path2 = paths("svr", "fft") * paths("fft", "dac") * paths("dac", "out")
        path1 + path2
    }
}