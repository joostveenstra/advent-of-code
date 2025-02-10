package year2024

import framework.Day
import util.*
import kotlin.math.absoluteValue

object Day20 : Day {
    private fun CharGrid.findPath(): IntGrid {
        val start = findPoint('S')
        val end = findPoint('E')
        val path = asMutableGrid { Int.MAX_VALUE }.also { it[start] = 0 }

        tailrec fun CharGrid.path(position: Point, direction: Direction, steps: Int = 1) {
            if (position != end) {
                val nextDirection = listOf(
                    direction,
                    direction.turnLeft(),
                    direction.turnRight()
                ).first { get(position + it) != '#' }
                val nextPosition = position + nextDirection
                path[nextPosition] = steps
                path(nextPosition, nextDirection, steps + 1)
            }
        }

        path(start, RIGHT)
        return path
    }

    private fun IntGrid.isValidCheat(point: Point, diff: Point): Boolean {
        val other = point + diff
        return other in this && get(other) != Int.MAX_VALUE
                && (get(other) - get(point)).absoluteValue - (point manhattan other) >= 100
    }

    override fun part1(input: String) = with(input.toCharGrid().findPath()) {
        findPointsBy { it != Int.MAX_VALUE }.sumOf { point ->
            listOf(Point(2, 0), Point(0, 2)).count { isValidCheat(point, it) }
        }
    }

    override fun part2(input: String) = with(input.toCharGrid().findPath()) {
        findPointsBy { it != Int.MAX_VALUE }.sumOf { point ->
            val right = (2..20).count { x -> isValidCheat(point, Point(x, 0)) }
            val down = (1..20).sumOf { y ->
                (y - 20..20 - y).count { x -> isValidCheat(point, Point(x, y)) }
            }
            right + down
        }
    }
}