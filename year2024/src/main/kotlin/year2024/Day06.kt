package year2024

import framework.Context
import framework.Day
import util.*

class Day06(context: Context) : Day by context {
    val grid = input.toCharGrid()
    val path = grid.findPath()

    fun CharGrid.findPath(): Map<Point, Direction> {
        val start = findPoint('^')
        val path = mutableMapOf(start to UP)

        tailrec fun step(position: Point, direction: Direction): Map<Point, Direction> {
            val next = position + direction
            path.putIfAbsent(position, direction)
            return when (getOrNull(next)) {
                null -> path
                '#' -> step(position, direction.turnRight())
                else -> step(next, direction)
            }
        }

        return step(start, UP)
    }

    fun CharGrid.hasCycle(obstacle: Point, direction: Direction): Boolean {
        val seen = mutableSetOf<Pair<Point, Direction>>()

        tailrec fun step(position: Point, direction: Direction): Boolean {
            val next = position + direction
            return when {
                next !in this -> false
                next == obstacle || getOrNull(next) == '#' ->
                    if (!seen.add(position to direction)) true
                    else step(position, direction.turnRight())

                else -> step(next, direction)
            }
        }

        return step(obstacle - direction, direction)
    }

    fun part1() = path.size
    fun part2() = path.entries.parallelStream().skip(1).filter { (p, d) -> grid.hasCycle(p, d) }.count()
}