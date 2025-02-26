package year2016

import framework.Context
import framework.Day
import util.Point
import util.cardinalNeighbours
import util.dequeOf
import util.drain

class Day13(context: Context) : Day by context {
    val steps = 50
    val start = Point(1, 1)
    val goal = if (isExample) Point(7, 4) else Point(31, 39)
    val favorite = input.toInt()

    fun Point.isOpenSpace(favorite: Int): Boolean {
        val number = (x * x) + (3 * x) + (2 * x * y) + y + (y * y) + favorite
        return number.countOneBits() % 2 == 0
    }

    fun Point.isValid() = x >= 0 && y >= 0
    fun Point.validNeighbours(favorite: Int) = cardinalNeighbours.filter { it.isValid() }.filter { it.isOpenSpace(favorite) }

    fun findShortestPathTo(goal: Point, favorite: Int): Int {
        val queue = dequeOf(start)
        val visited = mutableMapOf(start to 0)

        queue.drain { point ->
            if (point == goal) return visited.getValue(point)
            val cost = visited.getValue(point) + 1
            point.validNeighbours(favorite).forEach { next ->
                if (next !in visited || cost < visited.getValue(next)) {
                    visited[next] = cost
                    queue.add(next)
                }
            }
        }

        error("This should never happen")
    }

    fun findNumberOfPossibleGoals(favorite: Int): Int {
        val queue = dequeOf(start)
        val visited = mutableMapOf(start to 0)

        queue.drain { point ->
            val cost = visited.getValue(point) + 1
            if (cost <= steps) {
                point.validNeighbours(favorite).forEach { next ->
                    if (next !in visited || cost < visited.getValue(next)) {
                        visited[next] = cost
                        queue.add(next)
                    }
                }
            }
        }

        return visited.size
    }

    fun part1() = findShortestPathTo(goal, favorite)
    fun part2() = findNumberOfPossibleGoals(favorite)
}