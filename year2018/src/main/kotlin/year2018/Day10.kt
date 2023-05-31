package year2018

import framework.Day
import util.Point
import util.allInts

object Day10 : Day<Any> {
    data class PointOfLight(val position: Point, val velocity: Point) {
        fun next() = PointOfLight(position + velocity, velocity)
    }

    private fun String.findMessage(): Pair<String, Int> {
        val target = if (lines().size == 31) 64L else 550L
        var points = lines().map { line ->
            val (x, y, dx, dy) = line.allInts().toList()
            PointOfLight(Point(x, y), Point(dx, dy))
        }
        var area = target
        var (left, right, top, bottom, time) = arrayOf(0, 0, 0, 0, 0)

        while (area >= target) {
            points = points.map { it.next() }
            left = points.minOf { it.position.x }
            right = points.maxOf { it.position.x }
            top = points.minOf { it.position.y }
            bottom = points.maxOf { it.position.y }
            area = (right - left).toLong() * (bottom - top)
            time++
        }

        val message = (top..bottom).joinToString("\n") { y ->
            (left..right).joinToString("") { x ->
                if (points.any { it.position == Point(x, y) }) "#" else " "
            }
        }

        return message to time
    }

    override fun part1(input: String) = input.findMessage().first

    override fun part2(input: String) = input.findMessage().second
}