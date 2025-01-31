package year2023

import framework.Day
import util.*

object Day14 : Day {
    private val order = listOf(NORTH, WEST, SOUTH, EAST)

    private fun CharGrid.tilt(direction: Direction) = toMutableGrid().apply {
        tailrec fun slide(position: Point) {
            val next = position + direction
            if (getOrNull(next) == '.') {
                this[position] = '.'
                this[next] = 'O'
                slide(next)
            }
        }

        val points = points
        val pointsFlipped = pointsFlipped
        val progressions = listOf(
            points.asReversed(),
            pointsFlipped.asReversed(),
            points,
            pointsFlipped
        )
        // TODO: refactor progressions
        progressions[cardinal.indexOf(direction)].filter { this[it] == 'O' }.forEach(::slide)
    }

    private fun CharGrid.cycle(): CharGrid = order.fold(this) { grid, direction -> grid.tilt(direction) }

    private fun CharGrid.load() = findPoints('O').sumOf { height - it.y }

    override fun part1(input: String) = input.toCharGrid().tilt(NORTH).load()

    override fun part2(input: String) = input.toCharGrid().patternRepeating(1000000000) { it.cycle() }.load()
}