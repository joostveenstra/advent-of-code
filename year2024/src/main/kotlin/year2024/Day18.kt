package year2024

import framework.Context
import framework.Day
import util.*

class Day18(context: Context) : Day by context {
    val size = if (isExample) 7 else 71
    val grid = mutableGrid(size, size) { Int.MAX_VALUE }
    val bytes = input.allInts().chunked(2).map { (x, y) -> Point(x, y) }.toList()

    init {
        bytes.forEachIndexed { i, b -> grid[b] = i + 1 }
    }

    fun IntGrid.shortestPath(time: Int): Int? {
        val start = ORIGIN
        val end = Point(maxX, maxY)
        val queue = dequeOf(start to 0)
        val seen = asMutableBooleanGrid().also { it.enable(start) }

        queue.drain { (position, cost) ->
            if (position == end) return cost
            position.cardinalNeighbours.filter { it in this && time < this[it] && !seen[it] }.forEach { next ->
                queue += next to cost + 1
                seen.enable(next)
            }
        }

        return null
    }

    val time = if (isExample) 12 else 1024

    fun part1() = grid.shortestPath(time)

    val times = (0..<bytes.size).toList()
    val first = times.binarySearchFirst { grid.shortestPath(it + 1) == null }

    fun part2() = bytes[first].let { (x, y) -> "$x,$y" }
}