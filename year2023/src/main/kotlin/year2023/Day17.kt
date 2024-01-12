package year2023

import framework.Day
import util.*

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

    private fun Grid<Int>.minimize(least: Int, most: Int): Int {
        val end = Point(width - 1, height - 1)
        val queue = priorityQueueOf<Pair<Crucible, Int>> { it.second }
        val visited = mutableMapOf<Crucible, Int>()

        Orientation.entries.forEach { orientation ->
            val init = Crucible(ORIGIN, orientation) to 0
            queue.offer(init)
            visited += init
        }

        tailrec fun Crucible.move(step: Int, direction: Point, heat: Int) {
            if (step <= most) {
                val nextPosition = position + direction * step
                if (nextPosition in this@minimize) {
                    val nextHeat = heat + get(nextPosition)
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

    override fun part1(input: String) = input.toIntGrid().minimize(1, 3)

    override fun part2(input: String) = input.toIntGrid().minimize(4, 10)
}