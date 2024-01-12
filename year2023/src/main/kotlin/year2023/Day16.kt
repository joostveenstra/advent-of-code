package year2023

import framework.Day
import util.*

object Day16 : Day<Int> {
    private val vertical = listOf(UP, DOWN)

    data class Beam(val position: Point, val direction: Point)

    private fun Grid<Char>.energize(start: Beam): Int {
        val energized = mutableSetOf<Beam>()
        val stack = dequeOf(start)

        while (stack.isNotEmpty()) {
            val (position, direction) = stack.pop()
            val nextPosition = position + direction
            val nextDirections = when (safeGet(nextPosition)) {
                '.' -> listOf(direction)
                '/' -> listOf(if (direction in vertical) direction.turnRight() else direction.turnLeft())
                '\\' -> listOf(if (direction in vertical) direction.turnLeft() else direction.turnRight())
                '|' -> if (direction in vertical) listOf(direction) else listOf(UP, DOWN)
                '-' -> if (direction in vertical) listOf(RIGHT, LEFT) else listOf(direction)
                else -> listOf()
            }
            nextDirections.forEach { nextDirection ->
                val next = Beam(nextPosition, nextDirection)
                if (next !in energized) {
                    energized += next
                    stack.push(next)
                }
            }
        }

        return energized.map { it.position }.distinct().size
    }

    override fun part1(input: String) = input.toCharGrid().energize(Beam(Point(-1, 0), RIGHT))

    override fun part2(input: String): Int {
        val grid = input.toCharGrid()
        val top = grid.xs.map { x -> Beam(Point(x, -1), DOWN) }
        val left = grid.ys.map { y -> Beam(Point(-1, y), RIGHT) }
        val bottom = grid.xs.map { x -> Beam(Point(x, grid.height), UP) }
        val right = grid.ys.map { y -> Beam(Point(grid.width, y), LEFT) }

        return listOf(top, left, bottom, right).flatten().maxOf { grid.energize(it) }
    }
}