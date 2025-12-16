package year2025

import framework.Context
import framework.Day
import util.allInts
import util.product

class Day12(context: Context) : Day by context {
    val regions = input.allInts().drop(6).chunked(8).map { it.take(2).product() to it.drop(2) }

    fun part1() = regions.count { (area, required) -> area >= required.sumOf { it * 9 } }
}