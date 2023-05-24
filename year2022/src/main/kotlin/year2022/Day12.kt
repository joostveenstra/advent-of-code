package year2022

import framework.Day
import util.Point
import util.dequeOf

typealias Grid = Map<Point, Char>

object Day12 : Day<Int> {
    private fun String.toGrid(): Grid = buildMap {
        lines().forEachIndexed { y, row ->
            row.forEachIndexed { x, char ->
                put(Point(x, y), char)
            }
        }
    }

    private fun Char.height() = when (this) {
        'S' -> 'a'
        'E' -> 'z'
        else -> this
    }

    private fun Grid.shortestPathTo(goal: Char): Int {
        val start = entries.first { it.value == 'E' }.key
        val queue = dequeOf(start)
        val visited = mutableMapOf(start to 0)

        while (queue.isNotEmpty()) {
            val point = queue.removeFirst()
            if (getValue(point) == goal) return visited.getValue(point)
            point.cardinalNeighbours.filter { it in this }.filterNot { it in visited }.forEach { next ->
                if (getValue(point).height() - getValue(next).height() <= 1) {
                    queue.addLast(next)
                    visited[next] = visited.getValue(point) + 1
                }
            }
        }

        error("This should never happen")
    }

    override fun part1(input: String) = input.toGrid().shortestPathTo('S')

    override fun part2(input: String) = input.toGrid().shortestPathTo('a')
}