package year2016

import framework.Day
import util.*

object Day24 : Day {
    private fun String.toGraph(): Map<Int, Map<Int, Int>> {
        // TODO: convert to grid
        val grid = buildSet {
            lines().forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    if (char != '#') add(Point(x, y))
                }
            }
        }
        // TODO: convert to grid
        val nodes = buildMap {
            lines().forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    if (char.isDigit()) put(char.digitToInt(), Point(x, y))
                }
            }
        }
        return nodes.mapValues { (_, start) -> nodes.mapValues { (_, end) -> grid.findShortestPath(start, end) } }
    }

    private fun Set<Point>.findShortestPath(start: Point, end: Point): Int {
        val queue = dequeOf(start)
        val visited = mutableMapOf(start to 0)

        queue.drain { point ->
            if (point == end) return visited.getValue(point)
            val cost = visited.getValue(point) + 1
            point.cardinalNeighbours.filter { it in this }.forEach { next ->
                if (next !in visited || cost < visited.getValue(next)) {
                    visited[next] = cost
                    queue += next
                }
            }
        }

        error("This should never happen")
    }

    private fun Map<Int, Map<Int, Int>>.travelingSalesman(routes: List<List<Int>>) =
        routes.minOf { it.zipWithNext { a, b -> getValue(a).getValue(b) }.sum() }

    override fun part1(input: String): Int {
        val graph = input.toGraph()
        val routes = (graph.keys - 0).permutations().map { listOf(0) + it }
        return graph.travelingSalesman(routes)
    }

    override fun part2(input: String): Int {
        val graph = input.toGraph()
        val routes = (graph.keys - 0).permutations().map { listOf(0) + it + 0 }
        return graph.travelingSalesman(routes)
    }
}