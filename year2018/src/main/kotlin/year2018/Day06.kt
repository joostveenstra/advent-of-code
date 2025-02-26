package year2018

import framework.Context
import framework.Day
import util.*

class Day06(context: Context) : Day by context {
    val coordinates = lines.map { it.toPoint() }

    fun part1(): Int {
        val left = coordinates.minOf { it.x }
        val right = coordinates.maxOf { it.x }
        val top = coordinates.minOf { it.y }
        val bottom = coordinates.maxOf { it.y }
        val points = buildList { for (x in left..right) for (y in top..bottom) add(Point(x, y)) }

        return points
            .mapNotNull { point ->
                val (first, second) = coordinates.sortedBy { it manhattan point }
                val closest = first manhattan point < second manhattan point
                if (closest) first to point else null
            }
            .groupBy { it.first }.values.map { pairs -> pairs.map { it.second } }
            .filter { it.none { p -> p.x == left || p.x == right || p.y == top || p.y == bottom } }
            .maxOf { it.size }
    }

    fun part2(): Int {
        val max = if (isExample) 32 else 10000
        val meanX = coordinates.sumOf { it.x } / coordinates.size
        val meanY = coordinates.sumOf { it.y } / coordinates.size

        val start = Point(meanX, meanY)
        val queue = dequeOf(start)
        val visited = mutableSetOf(start)

        queue.drain { point ->
            point.cardinalNeighbours
                .filterNot { it in visited }
                .filter { next -> coordinates.sumOf { it manhattan next } < max }
                .forEach { next ->
                    queue.add(next)
                    visited.add(next)
                }
        }

        return visited.size
    }
}