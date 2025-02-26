package year2024

import framework.Context
import framework.Day
import util.*

class Day16(context: Context) : Day by context {
    data class Reindeer(val position: Point, val direction: Direction, val score: Int = 0)

    val grid = input.toCharGrid()
    val paths = grid.traverse()

    fun CharGrid.traverse(): Pair<Int, Int> {
        val start = findPoint('S')
        val end = findPoint('E')
        val forward = priorityQueueOf(Reindeer(start, EAST)) { it.score }
        val seen = mutableMapOf((start to EAST) to 0)
        var min = Int.MAX_VALUE

        forward.drain { (position, direction, score) ->
            if (score >= min) return@drain
            if (position == end) {
                min = score
                return@drain
            }
            listOf(
                Reindeer(position + direction, direction, score + 1),
                Reindeer(position, direction.turnLeft(), score + 1000),
                Reindeer(position, direction.turnRight(), score + 1000)
            ).forEach { next ->
                val (nextPosition, nextDirection, nextScore) = next
                if (this[nextPosition] != '#' && nextScore < (seen[nextPosition to nextDirection] ?: Int.MAX_VALUE)) {
                    forward.add(next)
                    seen[nextPosition to nextDirection] = nextScore
                }
            }
        }

        val backward = cardinal.mapNotNull { direction ->
            seen[end to direction]?.let { Reindeer(end, direction, it) }
        }.toDeque()
        val path = mutableSetOf<Point>()

        backward.drain { (position, direction, score) ->
            path.add(position)
            if (position == start) return@drain
            listOf(
                Reindeer(position - direction, direction, score - 1),
                Reindeer(position, direction.turnLeft(), score - 1000),
                Reindeer(position, direction.turnRight(), score - 1000)
            ).forEach { next ->
                val (nextPosition, nextDirection, nextScore) = next
                if (nextScore == seen[nextPosition to nextDirection]) {
                    backward.add(next)
                    seen[nextPosition to nextDirection] = Int.MAX_VALUE
                }
            }
        }

        return min to path.size
    }

    fun part1() = paths.let { (lowest) -> lowest }
    fun part2() = paths.let { (_, tiles) -> tiles }
}