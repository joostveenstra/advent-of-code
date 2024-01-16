package year2022

import framework.Day
import util.Grid
import util.dequeOf
import util.toCharGrid

object Day12 : Day<Int> {
    private fun Char.height() = when (this) {
        'S' -> 'a'
        'E' -> 'z'
        else -> this
    }

    private fun Grid<Char>.shortestPathTo(goal: Char): Int {
        val start = find('E')
        val queue = dequeOf(start)
        val visited = mutableMapOf(start to 0)

        while (queue.isNotEmpty()) {
            val point = queue.removeFirst()
            if (get(point) == goal) return visited.getValue(point)
            point.cardinalNeighbours.filter { it in this }.filterNot { it in visited }.forEach { next ->
                if (get(point).height() - get(next).height() <= 1) {
                    queue.addLast(next)
                    visited[next] = visited.getValue(point) + 1
                }
            }
        }

        error("This should never happen")
    }

    override fun part1(input: String) = input.toCharGrid().shortestPathTo('S')

    override fun part2(input: String) = input.toCharGrid().shortestPathTo('a')
}