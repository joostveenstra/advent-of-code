package year2016

import framework.Day
import util.Point
import util.dequeOf
import util.permutations

object Day24 : Day<Int> {
    private fun String.toGraph(): Map<Int, Map<Int, Int>> {
        val grid = buildSet {
            lines().forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    if (char != '#') add(Point(x, y))
                }
            }
        }
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

        while (queue.isNotEmpty()) {
            val point = queue.removeFirst()
            if (point == end) return visited.getValue(point)
            val cost = visited.getValue(point) + 1
            point.cardinalNeighbours.filter { it in this }.forEach { next ->
                if (next !in visited || cost < visited.getValue(next)) {
                    visited[next] = cost
                    queue.addLast(next)
                }
            }
        }

        error("This should never happen")
    }

    private fun Map<Int, Map<Int, Int>>.travelingSalesman(routes: Sequence<List<Int>>) =
        routes.minOf { it.windowed(2).sumOf { (a, b) -> getValue(a).getValue(b) } }

    override fun part1(input: String): Int {
        val graph = input.toGraph()
        val routes = (graph.keys - 0).toList().permutations().map { listOf(0) + it }
        return graph.travelingSalesman(routes)
    }

    override fun part2(input: String): Int {
        val graph = input.toGraph()
        val routes = (graph.keys - 0).toList().permutations().map { listOf(0) + it + 0 }
        return graph.travelingSalesman(routes)
    }
}