package year2023

import framework.Day
import util.*

object Day16 : Day {
    data class Beam(val position: Point, val direction: Direction)

    private fun CharGrid.energize(start: Beam): Int {
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
                if (next !in energized) {
                    energized += next
                    stack.push(next)
                }
            }
        }

        return energized.map { it.position }.toSet().size
    }

    override fun part1(input: String) = input.toCharGrid().energize(Beam(Point(-1, 0), RIGHT))

    override fun part2(input: String) = with(input.toCharGrid()) {
        val top = xRange.map { x -> Beam(Point(x, -1), DOWN) }
        val left = yRange.map { y -> Beam(Point(-1, y), RIGHT) }
        val bottom = xRange.map { x -> Beam(Point(x, height), UP) }
        val right = yRange.map { y -> Beam(Point(width, y), LEFT) }

        listOf(top, left, bottom, right).flatten().maxOf { energize(it) }
    }
}