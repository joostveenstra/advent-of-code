package year2022

import framework.Context
import framework.Day
import util.*

class Day12(context: Context) : Day by context {
    val grid = input.toCharGrid()

    fun Char.height() = when (this) {
        'S' -> 'a'
        'E' -> 'z'
        else -> this
    }

    fun CharGrid.shortestPathTo(goal: Char): Int {
        val start = findPoint('E')
        val queue = dequeOf(start)
        val visited = mutableMapOf(start to 0)

        queue.drain { point ->
            if (get(point) == goal) return visited.getValue(point)
            point.cardinalNeighbours.filter { it in this }.filterNot { it in visited }.forEach { next ->
                if (get(point).height() - get(next).height() <= 1) {
                    queue.add(next)
                    visited[next] = visited.getValue(point) + 1
                }
            }
        }

        error("This should never happen")
    }

    fun part1() = grid.shortestPathTo('S')
    fun part2() = grid.shortestPathTo('a')
}