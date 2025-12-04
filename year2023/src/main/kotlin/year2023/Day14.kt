package year2023

import framework.Context
import framework.Day
import util.*

class Day14(context: Context) : Day by context {
    val order = listOf(NORTH, WEST, SOUTH, EAST)
    val grid = input.toCharGrid()
    val points = grid.points
    val pointsFlipped = grid.pointsFlipped
    val progressions = listOf(
        points.asReversed(),
        pointsFlipped.asReversed(),
        points,
        pointsFlipped
    )

    fun CharGrid.tilt(direction: Direction) = toMutableGrid().apply {
        tailrec fun slide(position: Point) {
            val next = position + direction
            if (getOrNull(next) == '.') {
                this[position] = '.'
                this[next] = 'O'
                slide(next)
            }
        }

        progressions[dCardinal.indexOf(direction)].filter { this[it] == 'O' }.forEach(::slide)
    }

    fun CharGrid.cycle() = order.fold(this) { grid, direction -> grid.tilt(direction) }

    fun CharGrid.load() = findPoints('O').sumOf { height - it.y }

    fun part1() = grid.tilt(NORTH).load()
    fun part2() = grid.patternRepeating(1000000000) { it.cycle() }.load()
}