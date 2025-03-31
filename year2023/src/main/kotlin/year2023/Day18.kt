package year2023

import framework.Context
import framework.Day
import util.*
import kotlin.math.absoluteValue

class Day18(context: Context) : Day by context {
    val plans = lines.map { line ->
        val (a, b, c) = line.split(' ')
        val first = a.first().toDirection() to b.toInt()
        val direction = cardinal[c[7].digitToInt()]
        val steps = c.substring(2..<c.length - 2).toInt(16)
        val second = direction to steps
        first to second
    }.unzip()

    fun determinant(a: Point, b: Point) = a.x.toLong() * b.y - a.y.toLong() * b.x

    fun List<Pair<Direction, Int>>.dig(): Long {
        val points = scan(ORIGIN) { position, (direction, steps) -> position + direction * steps }
        val area = points.zipWithNext().sumOf { (a, b) -> determinant(a, b) }
        val perimeter = sumOf { it.second }
        return area.absoluteValue / 2 + perimeter / 2 + 1
    }

    fun part1() = plans.first.dig()
    fun part2() = plans.second.dig()
}