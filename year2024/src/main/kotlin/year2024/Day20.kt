package year2024

import framework.Context
import framework.Day
import util.*
import kotlin.math.absoluteValue

class Day20(context: Context) : Day by context {
    val track = input.toCharGrid().findPath()
    val points = track.findPointsBy { it != Int.MAX_VALUE }

    fun CharGrid.findPath(): IntGrid {
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

    fun IntGrid.isValidCheat(point: Point, diff: Point): Boolean {
        val other = point + diff
        return other in this && get(other) != Int.MAX_VALUE
                && (get(other) - get(point)).absoluteValue - (point manhattan other) >= 100
    }

    fun part1() = points.sumOf { point ->
        listOf(Point(2, 0), Point(0, 2)).count { track.isValidCheat(point, it) }
    }

    fun part2() = points.sumOf { point ->
        val right = (2..20).count { x -> track.isValidCheat(point, Point(x, 0)) }
        val down = (1..20).sumOf { y ->
            (y - 20..20 - y).count { x -> track.isValidCheat(point, Point(x, y)) }
        }
        right + down
    }
}