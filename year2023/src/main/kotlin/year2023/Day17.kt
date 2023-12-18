package year2023

import framework.Day
import util.ORIGIN
import util.Point
import util.cardinal
import util.priorityQueueOf

object Day17 : Day<Int> {
    data class Crucible(val position: Point, val direction: Point)

    private fun String.toBlocks() = buildMap {
        lines().forEachIndexed { y, row ->
            row.forEachIndexed { x, char -> put(Point(x, y), char.digitToInt()) }
        }
    }

    private fun Map<Point, Int>.minimize(least: Int, most: Int): Int {
        val start = Crucible(ORIGIN, ORIGIN)
        val end = Point(keys.maxOf { it.x }, keys.maxOf { it.y })
        val queue = priorityQueueOf(start to 0) { it.second }
        val visited = mutableMapOf(start to 0)

        while (queue.isNotEmpty()) {
            val (crucible) = queue.poll()
            if (crucible.position == end) return visited.getValue(crucible)
            crucible.direction.let { cardinal - it - it.opposite }.forEach { direction ->
                var heat = visited.getValue(crucible)
                for (step in 1..most) {
                    val position = crucible.position + direction * step
                    if (position in this) {
                        heat += getValue(position)
                        if (step >= least) {
                            val next = Crucible(position, direction)
                            if (next !in visited || heat < visited.getValue(next)) {
                                visited[next] = heat
                                queue.offer(next to heat)
                            }
                        }
                    }
                }
            }
        }

        error("This should never happen")
    }

    override fun part1(input: String) = input.toBlocks().minimize(1, 3)

    override fun part2(input: String) = input.toBlocks().minimize(4, 10)
}