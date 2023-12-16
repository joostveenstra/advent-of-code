package year2023

import framework.Day
import util.*

object Day16 : Day<Int> {
    private val vertical = listOf(NORTH, SOUTH)

    data class Beam(val position: Point, val direction: Point)

    private fun String.toTiles() = buildMap {
        lines().forEachIndexed { y, row ->
            row.forEachIndexed { x, char -> put(Point(x, y), char) }
        }
    }

    private fun Beam.next(tiles: Map<Point, Char>): List<Beam> {
        val nextPosition = position + direction
        val nextDirections = when (tiles[nextPosition]) {
            '.' -> listOf(direction)
            '/' -> listOf(if (direction in vertical) direction.turnRight() else direction.turnLeft())
            '\\' -> listOf(if (direction in vertical) direction.turnLeft() else direction.turnRight())
            '|' -> if (direction in vertical) listOf(direction) else listOf(NORTH, SOUTH)
            '-' -> if (direction in vertical) listOf(EAST, WEST) else listOf(direction)
            else -> listOf()
        }
        return nextDirections.map { nextDirection -> Beam(nextPosition, nextDirection) }
    }

    private fun Map<Point, Char>.energize(start: Beam): Int {
        val energized = mutableSetOf<Beam>()
        val stack = dequeOf(start)

        while (stack.isNotEmpty()) {
            val beam = stack.pop()
            beam.next(this).filterNot { it in energized }.forEach { next ->
                energized += next
                stack.push(next)
            }
        }

        return energized.map { it.position }.distinct().size
    }

    override fun part1(input: String) = input.toTiles().energize(Beam(Point(-1, 0), RIGHT))

    override fun part2(input: String): Int {
        val lines = input.lines()
        val tiles = input.toTiles()
        val width = lines.first().length
        val height = lines.size
        val top = (0..<width).map { x -> Beam(Point(x, -1), DOWN) }
        val left = (0..<height).map { y -> Beam(Point(-1, y), RIGHT) }
        val bottom = (0..<width).map { x -> Beam(Point(x, height), UP) }
        val right = (0..<height).map { y -> Beam(Point(width, y), LEFT) }

        return listOf(top, left, bottom, right).flatten().maxOf { tiles.energize(it) }
    }
}