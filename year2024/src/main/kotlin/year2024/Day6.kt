package year2024

import framework.Day
import util.*

object Day6 : Day {
    private fun CharGrid.findPath(): Map<Point, Direction> {
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

    private fun CharGrid.hasCycle(obstacle: Point, direction: Direction): Boolean {
        val seen = mutableSetOf<Pair<Point, Direction>>()

        tailrec fun step(position: Point, direction: Direction): Boolean {
            val next = position + direction
            return when {
                next !in this -> false
                next == obstacle || getOrNull(next) == '#' ->
                    if (position to direction in seen) {
                        true
                    } else {
                        seen += position to direction
                        step(position, direction.turnRight())
                    }

                else -> step(next, direction)
            }
        }

        return step(obstacle - direction, direction)
    }

    override fun part1(input: String) = input.toCharGrid().findPath().size

    override fun part2(input: String) = with(input.toCharGrid()) {
        findPath().entries.parallelStream().skip(1).filter { (p, d) -> hasCycle(p, d) }.count()
    }
}