package year2023

import framework.Day
import util.*
import year2023.Day17.Orientation.HORIZONTAL
import year2023.Day17.Orientation.VERTICAL

object Day17 : Day<Int> {
    enum class Orientation {
        HORIZONTAL, VERTICAL;

        fun turn() = when (this) {
            HORIZONTAL -> listOf(UP, DOWN)
            VERTICAL -> listOf(LEFT, RIGHT)
        }

        fun orthogonal() = if (this == HORIZONTAL) VERTICAL else HORIZONTAL
    }

    data class Crucible(val position: Point, val orientation: Orientation)

    private fun String.toBlocks() = buildMap {
        lines().forEachIndexed { y, row ->
            row.forEachIndexed { x, char -> put(Point(x, y), char.digitToInt()) }
        }
    }

    private fun Map<Point, Int>.minimize(least: Int, most: Int): Int {
        val initial = listOf(Crucible(ORIGIN, HORIZONTAL), Crucible(ORIGIN, VERTICAL)).map { it to 0 }
        val end = Point(keys.maxOf { it.x }, keys.maxOf { it.y })
        val queue = priorityQueueOf<Pair<Crucible, Int>> { it.second }.apply { addAll(initial) }
        val visited = mutableMapOf<Crucible, Int>().apply { putAll(initial) }

        tailrec fun Crucible.move(step: Int, direction: Point, heat: Int) {
            if (step <= most) {
                val nextPosition = position + direction * step
                if (nextPosition in this@minimize) {
                    val nextHeat = heat + getValue(nextPosition)
                    if (step >= least) {
                        val next = Crucible(nextPosition, orientation.orthogonal())
                        if (next !in visited || nextHeat < visited.getValue(next)) {
                            visited[next] = nextHeat
                            val priority = nextHeat + next.position.manhattan(end)
                            queue.offer(next to priority)
                        }
                    }
                    move(step + 1, direction, nextHeat)
                }
            }
        }

        while (queue.isNotEmpty()) {
            val (crucible) = queue.poll()
            if (crucible.position == end) return visited.getValue(crucible)
            crucible.orientation.turn().forEach { direction ->
                crucible.move(1, direction, visited.getValue(crucible))
            }
        }

        error("This should never happen")
    }

    override fun part1(input: String) = input.toBlocks().minimize(1, 3)

    override fun part2(input: String) = input.toBlocks().minimize(4, 10)
}