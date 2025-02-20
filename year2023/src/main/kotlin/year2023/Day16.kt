package year2023

import framework.Context
import framework.Day
import util.*

class Day16(context: Context) : Day by context {
    data class Beam(val position: Point, val direction: Direction)

    val grid = this@Day16.input.toCharGrid()

    fun CharGrid.energize(start: Beam): Int {
        val energized = mutableSetOf<Beam>()
        val stack = dequeOf(start)

        stack.drain { (position, direction) ->
            val nextPosition = position + direction
            val nextDirections = when (getOrNull(nextPosition)) {
                '.' -> listOf(direction)
                '/' -> listOf(if (direction.isVertical) direction.turnRight() else direction.turnLeft())
                '\\' -> listOf(if (direction.isHorizontal) direction.turnRight() else direction.turnLeft())
                '|' -> if (direction.isVertical) listOf(direction) else vertical
                '-' -> if (direction.isHorizontal) listOf(direction) else horizontal
                else -> listOf()
            }
            nextDirections.forEach { nextDirection ->
                val next = Beam(nextPosition, nextDirection)
                if (energized.add(next)) stack.push(next)
            }
        }

        return energized.map { it.position }.toSet().size
    }

    fun part1() = grid.energize(Beam(Point(-1, 0), RIGHT))
    fun part2() = with(grid) {
        val top = xRange.map { x -> Beam(Point(x, -1), DOWN) }
        val left = yRange.map { y -> Beam(Point(-1, y), RIGHT) }
        val bottom = xRange.map { x -> Beam(Point(x, height), UP) }
        val right = yRange.map { y -> Beam(Point(width, y), LEFT) }

        listOf(top, left, bottom, right).flatten().maxOf { energize(it) }
    }
}