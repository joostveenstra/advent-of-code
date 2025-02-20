package year2015

import framework.Context
import framework.Day
import util.ORIGIN
import util.plus
import util.toDirection

class Day03(context: Context) : Day by context {
    fun Iterable<Char>.deliver() = map { it.toDirection() }.scan(ORIGIN) { position, direction -> position + direction }

    fun part1() = input.asIterable().deliver().toSet().size
    fun part2() = run {
        val (santa, robot) = this.input.withIndex().partition { it.index % 2 == 0 }.let { (left, right) ->
            left.map { it.value } to right.map { it.value }
        }
        (santa.deliver() + robot.deliver()).toSet().size
    }
}