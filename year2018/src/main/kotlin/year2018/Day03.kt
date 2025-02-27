package year2018

import framework.Context
import framework.Day
import util.Point
import util.allInts
import util.frequencies

class Day03(context: Context) : Day by context {
    val claims = input.lineSequence().map { line ->
        val (_, left, top, width, height) = line.allInts().toList()
        sequence { for (x in left until left + width) for (y in top until top + height) yield(Point(x, y)) }
    }
    val groups = claims.flatten().frequencies()

    fun part1() = groups.count { (_, count) -> count > 1 }
    fun part2() = 1 + claims.indexOfFirst { claim -> claim.all { point -> groups[point] == 1 } }
}