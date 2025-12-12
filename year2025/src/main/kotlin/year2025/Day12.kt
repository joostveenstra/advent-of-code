package year2025

import framework.Context
import framework.Day
import util.EMPTY_LINE
import util.allInts
import util.product

class Day12(context: Context) : Day by context {
    val blocks = input.split(EMPTY_LINE)
    val shapes = blocks.dropLast(1).map { s -> s.count { it == '#' } }
    val regions = blocks.last().lines().map { l ->
        l.allInts().toList().let { it.take(2).product() to it.drop(2) }
    }

    fun part1() = regions.count { (area, required) -> area >= required.zip(shapes) { r, s -> r * s }.sum() }
}