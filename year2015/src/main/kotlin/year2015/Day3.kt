package year2015

import framework.Day
import util.Point

object Day3 : Day<Int> {
    private fun Iterable<Char>.deliver() = map(Point::of).scan(Point(0, 0)) { position, move -> position + move }

    override fun part1(input: String) = input.asIterable().deliver().distinct().size

    override fun part2(input: String): Int {
        val (santa, robot) = input.withIndex().partition { it.index % 2 == 0 }.let { (left, right) -> left.map { it.value } to right.map { it.value } }
        return (santa.deliver() + robot.deliver()).distinct().size
    }
}