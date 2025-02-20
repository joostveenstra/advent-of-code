package year2016

import framework.Context
import framework.Day
import util.*

class Day01(context: Context) : Day by context {
    data class State(val positions: List<Point>, val direction: Direction)

    val initial = State(listOf(ORIGIN), NORTH)
    val moves = input.split(", ").map { it.first() to it.drop(1).toInt() }
    val path = moves.walk()

    fun List<Pair<Char, Int>>.walk() = scan(initial) { (positions, direction), (turn, steps) ->
        val nextDirection = if (turn == 'L') direction.turnLeft() else direction.turnRight()
        val nextPositions = (1..steps).map { positions.last() + nextDirection * it }
        State(nextPositions, nextDirection)
    }.flatMap { it.positions }

    fun part1() = path.last().manhattan
    fun part2() = path.groupingBy { it }.eachCount().filterValues { it > 1 }.keys.first().manhattan
}