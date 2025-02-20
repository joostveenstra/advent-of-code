package year2022

import framework.Context
import framework.Day
import util.Point
import util.cardinalNeighbours

class Day24(context: Context) : Day by context {
    val start = Point(1, 0)
    val valley = lines
    val width = valley.first().length - 2
    val height = valley.size - 2
    val goal = Point(width, height + 1)

    fun Point.possibleMoves() = cardinalNeighbours + this

    fun Point.isValid(time: Int) =
        y in valley.indices
                && valley[y][x] != '#'
                && valley[y][(x - 1 + time).mod(width) + 1] != '<'
                && valley[y][(x - 1 - time).mod(width) + 1] != '>'
                && valley[(y - 1 + time).mod(height) + 1][x] != '^'
                && valley[(y - 1 - time).mod(height) + 1][x] != 'v'

    tailrec fun simulate(state: Set<Point>, goal: Point, time: Int): Int =
        if (goal in state) time else {
            val next = state.flatMap { it.possibleMoves() }.filter { it.isValid(time + 1) }.toSet()
            simulate(next, goal, time + 1)
        }

    fun part1() = simulate(setOf(start), goal, 0)
    fun part2(): Int {
        val timeAtGoal = simulate(setOf(start), goal, 0)
        val timeBackAtStart = simulate(setOf(goal), start, timeAtGoal)
        return simulate(setOf(start), goal, timeBackAtStart)
    }
}