package year2024

import framework.Day
import util.*

object Day18 : Day {
    private fun parse(input: String): Pair<IntGrid, List<Point>> {
        val grid = mutableGrid(71, 71) { Int.MAX_VALUE }
        val bytes = input.allInts().chunked(2).map { (x, y) -> Point(x, y) }.toList()
        bytes.forEachIndexed { i, b -> grid[b] = i }
        return grid to bytes
    }

    private fun IntGrid.shortestPath(time: Int): Int? {
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

    override fun part1(input: String) = parse(input).first.shortestPath(1024)

    override fun part2(input: String) = parse(input).let { (grid, bytes) ->
        val times = (0..bytes.size).toList()
        val first = times.binarySearchFirst { grid.shortestPath(it) == null }
        bytes[first].let { (x, y) -> "$x,$y" }
    }
}