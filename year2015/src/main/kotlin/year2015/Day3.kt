package year2015

import framework.Day
import util.ORIGIN
import util.plus
import util.toDirection

object Day3 : Day {
    private fun Iterable<Char>.deliver() = map { it.toDirection() }.scan(ORIGIN) { position, direction -> position + direction }

    override fun part1(input: String) = input.asIterable().deliver().toSet().size

    override fun part2(input: String): Int {
        val (santa, robot) = input.withIndex().partition { it.index % 2 == 0 }.let { (left, right) -> left.map { it.value } to right.map { it.value } }
        return (santa.deliver() + robot.deliver()).toSet().size
    }
}