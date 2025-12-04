package year2016

import framework.Context
import framework.Day
import util.*

class Day24(context: Context) : Day by context {
    val graph = run {
        val grid = buildSet {
            lines.forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    if (char != '#') add(Point(x, y))
                }
            }
        }
        val nodes = buildMap {
            lines.forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    if (char.isDigit()) put(char.digitToInt(), Point(x, y))
                }
            }
        }
        nodes.mapValues { (_, start) -> nodes.mapValues { (_, end) -> grid.findShortestPath(start, end) } }
    }

    fun Set<Point>.findShortestPath(start: Point, end: Point): Int {
        val queue = priorityQueueOf(start to 0) { it.second }
        val visited = mutableSetOf(start)

        queue.drain { (point, cost) ->
            if (point == end) return cost
            point.cardinal.filter { it in this }.forEach { next ->
                if (visited.add(next)) {
                    queue.add(next to cost + 1)
                }
            }
        }

        error("This should never happen")
    }

    fun Map<Int, Map<Int, Int>>.travelingSalesman(routes: List<List<Int>>) =
        routes.minOf { it.zipWithNext { a, b -> getValue(a).getValue(b) }.sum() }

    fun part1() = run {
        val routes = (graph.keys - 0).permutations().map { listOf(0) + it }
        graph.travelingSalesman(routes)
    }

    fun part2() = run {
        val routes = (graph.keys - 0).permutations().map { listOf(0) + it + 0 }
        graph.travelingSalesman(routes)
    }
}