package year2017

import framework.Day
import util.*

object Day22 : Day {
    sealed interface Node
    data object Clean : Node
    data object Weakened: Node
    data object Infected : Node
    data object Flagged: Node

    data class State(val grid: MutableMap<Point, Node>, val position: Point, val direction: Direction, val infected: Int) {
        fun next(node: Node, direction: Direction) =
            State(grid.also { it[position] = node }, position + direction, direction, infected + if (node is Infected) 1 else 0)
    }

    private fun String.toInitial(): State {
        val lines = lines()
        // TODO: Convert to grid
        val grid = buildMap {
            lines.forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    if (char == '#') put(Point(x, y), Infected)
                }
            }
        }
        val start = Point(lines.size / 2, lines.size / 2)
        return State(grid.toMutableMap(), start, UP, 0)
    }

    override fun part1(input: String): Int {
        fun State.step() = when (grid.getOrDefault(position, Clean)) {
            is Clean -> next(Infected, direction.turnLeft())
            is Infected -> next(Clean, direction.turnRight())
            else -> throw IllegalStateException("This should never happen")
        }
        return generateSequence(input.toInitial()) { it.step() }.nth(10000).infected
    }

    override fun part2(input: String): Int {
        fun State.step() = when (grid.getOrDefault(position, Clean)) {
            is Clean -> next(Weakened, direction.turnLeft())
            is Weakened -> next(Infected, direction)
            is Infected -> next(Flagged, direction.turnRight())
            is Flagged -> next(Clean, direction.turnLeft().turnLeft())
        }
        return generateSequence(input.toInitial()) { it.step() }.nth(10000000).infected
    }
}