package year2024

import framework.Day
import util.allDistinctBy
import util.allInts
import util.product

object Day14 : Day {
    data class Robot(val x: Int, val y: Int, val dx: Int, val dy: Int) {
        fun step(n: Int = 1): Robot {
            val x = (x + n * dx).mod(101)
            val y = (y + n * dy).mod(103)
            return Robot(x, y, dx, dy)
        }

        fun quadrant() = when {
            x < 50 && y < 51 -> 1
            x > 50 && y < 51 -> 2
            x < 50 && y > 51 -> 3
            x > 50 && y > 51 -> 4
            else -> 0
        }

        fun hash() = 101 * y + x
    }

    private fun String.toRobots() = allInts().chunked(4).map { Robot(it[0], it[1], it[2], it[3]) }.toList()

    override fun part1(input: String) = input.toRobots()
        .map { it.step(100) }
        .groupingBy { it.quadrant() }.eachCount()
        .filterKeys { it != 0 }
        .values.product()

    override fun part2(input: String) = generateSequence(input.toRobots()) { it.map(Robot::step) }.indexOfFirst { it.allDistinctBy(Robot::hash) }
}