package year2015

import framework.Context
import framework.Day
import util.ORIGIN
import util.Point
import util.plus
import util.toDirection

class Day03(context: Context) : Day by context {
    val directions = input.map { it.toDirection() }

    fun List<Point>.deliver() = scan(ORIGIN) { position, direction -> position + direction }

    fun part1() = directions.deliver().toSet().size
    fun part2() = run {
        val (santa, robot) = directions.withIndex().partition { it.index % 2 == 0 }.let { (left, right) ->
            left.map { it.value } to right.map { it.value }
        }
        (santa.deliver() + robot.deliver()).toSet().size
    }
}