package year2023

import framework.Context
import framework.Day
import util.*

class Day17(context: Context) : Day by context {
    data class Crucible(val position: Point, val orientation: Orientation)

    val grid = input.toDigitGrid()

    fun IntGrid.minimize(least: Int, most: Int): Int {
        val end = Point(maxX, maxY)
        val queue = priorityQueueOf<Pair<Crucible, Int>> { it.second }
        val visited = mutableMapOf<Crucible, Int>()

        Orientation.entries.forEach { orientation ->
            val init = Crucible(ORIGIN, orientation)
            queue.add(init to 0)
            visited[init] = 0
        }

        tailrec fun Crucible.move(step: Int, direction: Direction, heat: Int) {
            if (step <= most) {
                val nextPosition = position + direction * step
                if (nextPosition in this@minimize) {
                    val nextHeat = heat + get(nextPosition)
                    if (step >= least) {
                        val next = Crucible(nextPosition, orientation.turn())
                        val previousHeat = visited[next]
                        if (previousHeat == null || nextHeat < previousHeat) {
                            visited[next] = nextHeat
                            val priority = nextHeat + (next.position manhattan end)
                            queue.add(next to priority)
                        }
                    }
                    move(step + 1, direction, nextHeat)
                }
            }
        }

        queue.drain { (crucible) ->
            if (crucible.position == end) return visited.getValue(crucible)
            crucible.orientation.orthogonal().forEach { direction ->
                crucible.move(1, direction, visited.getValue(crucible))
            }
        }

        error("This should never happen")
    }

    fun part1() = grid.minimize(1, 3)
    fun part2() = grid.minimize(4, 10)
}