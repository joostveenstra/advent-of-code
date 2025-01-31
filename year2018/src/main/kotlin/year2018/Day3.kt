package year2018

import framework.Day
import util.Point
import util.allInts

object Day3 : Day {
    private fun parse(input: String) = input.lineSequence().run {
        val claims = map { line ->
            val (_, left, top, width, height) = line.allInts().toList()
            sequence { for (x in left until left + width) for (y in top until top + height) yield(Point(x, y)) }
        }
        val groups = claims.flatten().groupingBy { it }.eachCount()
        claims to groups
    }

    override fun part1(input: String) = parse(input).let { (_, groups) ->
        groups.count { (_, count) -> count > 1 }
    }

    override fun part2(input: String) = parse(input).let { (claims, groups) ->
        1 + claims.indexOfFirst { claim -> claim.all { point -> groups[point] == 1 } }
    }
}