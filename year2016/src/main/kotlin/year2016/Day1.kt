package year2016

import framework.Day
import util.*

object Day1 : Day {
    private val initial = State(listOf(ORIGIN), NORTH)

    data class State (val positions: List<Point>, val direction: Direction)

    private fun String.toMoves() = split(", ").map { it.first() to it.drop(1).toInt() }

    private fun List<Pair<Char, Int>>.walk() = scan(initial) { (positions, direction), (turn, steps) ->
        val nextDirection = if (turn == 'L') direction.turnLeft() else direction.turnRight()
        val nextPositions = (1..steps).map { positions.last() + nextDirection * it }
        State(nextPositions, nextDirection)
    }.flatMap { it.positions }

    override fun part1(input: String) = input.toMoves().walk().last().manhattan

    override fun part2(input: String) = input.toMoves().walk().groupingBy { it }.eachCount().filterValues { it > 1 }.keys.first().manhattan
}