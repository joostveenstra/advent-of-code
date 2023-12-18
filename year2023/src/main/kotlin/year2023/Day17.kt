package year2023

import framework.Day
import util.Point
import util.RIGHT
import util.priorityQueueOf

object Day17 : Day<Int> {
    data class Crucible(val position: Point, val direction: Point, val steps: Int)
    data class State(val crucible: Crucible, val cost: Int)

    private fun String.toBlocks() = buildMap {
        lines().forEachIndexed { y, row ->
            row.forEachIndexed { x, char -> put(Point(x, y), char.digitToInt()) }
        }
    }

    private fun Crucible.next(blocks: Map<Point, Int>, least: Int, most: Int): List<Crucible> {
        val nextDirections = buildList {
            if (steps >= least || steps == 0) {
                add(direction.turnLeft())
                add(direction.turnRight())
            }
            if (steps < most) add(direction)
        }
        return nextDirections.filter { position + it in blocks }.map { nextDirection ->
            val nextPosition = position + nextDirection
            val nextSteps = if (nextDirection == direction) steps + 1 else 1
            Crucible(nextPosition, nextDirection, nextSteps)
        }
    }

    private fun Map<Point, Int>.minimize(least: Int, most: Int): Int {
        val start = Crucible(Point(0, 0), RIGHT, 0)
        val end = Point(keys.maxOf { it.x }, keys.maxOf { it.y })
        val initial = State(start, 0)
        val queue = priorityQueueOf(initial) { it.cost }
        val visited = mutableMapOf(start to 0)

        while (queue.isNotEmpty()) {
            val (crucible, cost) = queue.poll()
            if (crucible.position == end && crucible.steps >= least) return cost
            crucible.next(this, least, most).forEach { next ->
                val nextCost = cost + getValue(next.position)
                if (next !in visited || nextCost < visited.getValue(next)) {
                    visited[next] = nextCost
                    queue.offer(State(next, nextCost))
                }
            }
        }

        error("This should never happen")
    }

    override fun part1(input: String) = input.toBlocks().minimize(1, 3)

    override fun part2(input: String) = input.toBlocks().minimize(4, 10)
}