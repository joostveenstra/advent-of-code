package year2023

import framework.Day
import util.*
import kotlin.math.absoluteValue

object Day18 : Day<Long> {
    private fun String.toPlans() = lines().map { line ->
        val (a, b, c) = line.split(' ')
        val first = Point.of(a.first()) to b.toInt()
        val direction = when (c[7]) {
            '0' -> RIGHT
            '1' -> DOWN
            '2' -> LEFT
            else -> UP
        }
        val steps = c.substring(2..<c.length - 2).toInt(16)
        val second = direction to steps

        first to second
    }.unzip()

    private fun determinant(a: Point, b: Point) = a.x.toLong() * b.y - a.y.toLong() * b.x

    private fun List<Pair<Point, Int>>.dig(): Long {
        val points = scan(ORIGIN) { position, (direction, steps) -> position + direction * steps }
        val area = points.zipWithNext().fold(0L) { area, (a, b) -> area + determinant(a, b) }
        val perimeter = sumOf { it.second }
        return area.absoluteValue / 2 + perimeter / 2 + 1
    }

    override fun part1(input: String) = input.toPlans().first.dig()

    override fun part2(input: String) = input.toPlans().second.dig()
}