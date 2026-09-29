package year2021

import framework.Context
import framework.Day
import util.*

class Day15(context: Context) : Day by context {
    val grid = input.toDigitGrid()
    val start = Point(0, 0)

    fun IntGrid.shortestPath(): Int {
        val end = Point(maxX, maxY)
        val queue = priorityQueueOf(start to 0) { it.second }
        val seen = mutableSetOf<Point>()

        queue.drain { (position, risk) ->
            if (position == end) return risk
            position.cardinal().forEach { next ->
                if (seen.add(next)) {
                    val nextRisk = risk + get(next)
                    queue.add(next to nextRisk)
                }
            }
        }

        error("This should never happen")
    }

    fun IntGrid.expanded() = grid(5 * width, 5 * height) { (x, y) ->
        val base = this[Point(x % width, y % height)]
        val dx = x / width
        val dy = y / height
        val risk = base + dx + dy
        risk.takeIf { it < 10 } ?: (risk - 9)
    }

    fun part1() = grid.shortestPath()
    fun part2() = grid.expanded().shortestPath()
}