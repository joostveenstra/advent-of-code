package year2018

import framework.Day
import util.allInts

object Day10 : Day<Any> {
    data class PointOfLight(val x: Int, val y: Int, val dx: Int, val dy: Int) {
        fun next() = copy(x = x + dx, y = y + dy)
    }

    private fun String.findMessage(): Pair<String, Int> {
        val target = if (lines().size == 31) 64L else 550L
        var points = lines().map { line ->
            val (x, y, dx, dy) = line.allInts().toList()
            PointOfLight(x, y, dx, dy)
        }
        var area = target
        var (left, right, top, bottom, time) = arrayOf(0, 0, 0, 0, 0)

        while (area >= target) {
            points = points.map { it.next() }
            left = points.minOf { it.x }
            right = points.maxOf { it.x }
            top = points.minOf { it.y }
            bottom = points.maxOf { it.y }
            area = (right - left).toLong() * (bottom - top)
            time++
        }

        val message = (top..bottom).joinToString("\n") { y ->
            (left..right).joinToString("") { x ->
                if (points.any { it.x == x && it.y == y }) "#" else " "
            }
        }

        return message to time
    }

    override fun part1(input: String) = input.findMessage().first

    override fun part2(input: String) = input.findMessage().second
}