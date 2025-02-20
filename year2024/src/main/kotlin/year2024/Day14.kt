package year2024

import framework.Context
import framework.Day
import util.allDistinctBy
import util.allInts
import util.product

class Day14(context: Context) : Day by context {
    val robots = input.allInts().chunked(4).map { Robot(it[0], it[1], it[2], it[3]) }.toList()
    val width = if (isExample) 11 else 101
    val height = if (isExample) 7 else 103
    val midX = width / 2
    val midY = height / 2

    inner class Robot(val x: Int, val y: Int, val dx: Int, val dy: Int) {
        fun step(n: Int = 1): Robot {
            val x = (x + n * dx).mod(width)
            val y = (y + n * dy).mod(height)
            return Robot(x, y, dx, dy)
        }

        fun quadrant() = when {
            x < midX && y < midY -> 1
            x > midX && y < midY -> 2
            x < midX && y > midY -> 3
            x > midX && y > midY -> 4
            else -> 0
        }

        fun hash() = width * y + x
    }

    fun part1() = robots
        .map { it.step(100) }
        .groupingBy { it.quadrant() }.eachCount()
        .filterKeys { it != 0 }
        .values.product()

    fun part2() = generateSequence(robots) { it.map(Robot::step) }.indexOfFirst { it.allDistinctBy(Robot::hash) }
}