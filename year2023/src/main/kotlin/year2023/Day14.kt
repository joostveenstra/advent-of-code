package year2023

import framework.Day
import util.*

object Day14 : Day<Int> {
    private val directions = listOf(NORTH, WEST, SOUTH, EAST)

    private fun Grid<Char>.tilt(direction: Point) = apply {
        tailrec fun slide(position: Point) {
            val next = position + direction
            if (safeGet(next) == '.') {
                swap(position, next)
                slide(next)
            }
        }
        progressions[direction]?.filter { get(it) == 'O' }?.forEach(::slide)
    }

    private fun Grid<Char>.cycle() = directions.fold(this) { grid, direction -> grid.tilt(direction) }

    private fun Grid<Char>.rocks() = findAll('O')

    private fun List<Point>.load(height: Int) = sumOf { (height - it.y) }

    override fun part1(input: String): Int {
        val grid = input.toCharGrid()
        return grid.tilt(NORTH).rocks().load(grid.height)
    }

    override fun part2(input: String): Int {
        val grid = input.toCharGrid()
        val seen = mutableMapOf(grid.rocks() to 0)

        while (true) {
            grid.cycle()
            val rocks = grid.rocks()
            if (rocks in seen) break
            seen += rocks to seen.size
        }

        val start = seen.getValue(grid.rocks())
        val end = seen.size
        val cycleWidth = end - start
        val offset = 1000000000 - start
        val remainder = offset % cycleWidth
        val target = start + remainder
        val (rocks) = seen.entries.first { (_, i) -> i == target }
        return rocks.load(grid.height)
    }
}