package year2016

import framework.Day
import util.Point
import util.cardinalNeighbours
import util.dequeOf
import util.drain

object Day13 : Day {
    private val start = Point(1, 1)
    private const val STEPS = 50

    private fun String.toGoal() = if (toInt() == 10) Point(7, 4) else Point(31, 39)

    private fun Point.isOpenSpace(favorite: Int): Boolean {
        val number = (x * x) + (3 * x) + (2 * x * y) + y + (y * y) + favorite
        return number.countOneBits() % 2 == 0
    }

    private fun Point.isValid() = x >= 0 && y >= 0
    private fun Point.validNeighbours(favorite: Int) = cardinalNeighbours.filter { it.isValid() }.filter { it.isOpenSpace(favorite) }

    private fun findShortestPathTo(goal: Point, favorite: Int): Int {
        val queue = dequeOf(start)
        val visited = mutableMapOf(start to 0)

        queue.drain { point ->
            if (point == goal) return visited.getValue(point)
            val cost = visited.getValue(point) + 1
            point.validNeighbours(favorite).forEach { next ->
                if (next !in visited || cost < visited.getValue(next)) {
                    visited[next] = cost
                    queue += next
                }
            }
        }

        error("This should never happen")
    }

    private fun findNumberOfPossibleGoals(favorite: Int): Int {
        val queue = dequeOf(start)
        val visited = mutableMapOf(start to 0)

        queue.drain { point ->
            val cost = visited.getValue(point) + 1
            if (cost <= STEPS) {
                point.validNeighbours(favorite).forEach { next ->
                    if (next !in visited || cost < visited.getValue(next)) {
                        visited[next] = cost
                        queue += next
                    }
                }
            }
        }

        return visited.size
    }

    override fun part1(input: String) = findShortestPathTo(input.toGoal(), input.toInt())

    override fun part2(input: String) = findNumberOfPossibleGoals(input.toInt())
}