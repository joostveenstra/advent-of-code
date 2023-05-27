package year2018

import framework.Day
import util.Point
import util.dequeOf

object Day6 : Day<Int> {
    private fun String.toCoordinates() = lines().map(Point::of)

    override fun part1(input: String): Int {
        val coordinates = input.toCoordinates()
        val left = coordinates.minOf { it.x }
        val right = coordinates.maxOf { it.x }
        val top = coordinates.minOf { it.y }
        val bottom = coordinates.maxOf { it.y }
        val points = buildList { for (x in left..right) for (y in top..bottom) add(Point(x, y)) }

        return points
            .mapNotNull { point ->
                val (first, second) = coordinates.sortedBy { it.manhattan(point) }
                val closest = first.manhattan(point) < second.manhattan(point)
                if (closest) first to point else null
            }
            .groupBy { it.first }.values.map { pairs -> pairs.map { it.second } }
            .filter { it.none { p -> p.x == left || p.x == right || p.y == top || p.y == bottom } }
            .maxOf { it.size }
    }

    override fun part2(input: String): Int {
        val coordinates = input.toCoordinates()
        val max = if (coordinates.size == 6) 32 else 10000
        val meanX = coordinates.sumOf { it.x } / coordinates.size
        val meanY = coordinates.sumOf { it.y } / coordinates.size

        val start = Point(meanX, meanY)
        val queue = dequeOf(start)
        val visited = mutableSetOf(start)

        while (queue.isNotEmpty()) {
            queue.removeFirst()
                .cardinalNeighbours
                .filterNot { it in visited }
                .filter { next -> coordinates.sumOf { it.manhattan(next) } < max }
                .forEach { next ->
                    queue.addLast(next)
                    visited += next
                }
        }

        return visited.size
    }
}