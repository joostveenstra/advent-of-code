package year2025

import framework.Context
import framework.Day
import util.*

class Day07(context: Context) : Day by context {
    val grid = input.toCharGrid()
    val start = grid.findPoint('S')
    val cache = mutableMapOf<Point, Long>()
    var splits = 0

    fun beam(position: Point): Long = cache.getOrPut(position) {
        val next = position + DOWN
        when (grid.getOrNull(next)) {
            null -> 1
            '.' -> beam(next)
            else -> {
                splits += 1
                beam(next + LEFT) + beam(next + RIGHT)
            }
        }
    }

    val timelines = beam(start)

    fun part1() = splits
    fun part2() = timelines
}