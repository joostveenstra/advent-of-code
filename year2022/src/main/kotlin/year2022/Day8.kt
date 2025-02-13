package year2022

import framework.Day
import util.*

object Day8 : Day {
    private fun Point.canLookOutside(grid: IntGrid, direction: Point, height: Int): Boolean {
        val next = this + direction
        return grid.getOrNull(next)?.let { if (it < height) next.canLookOutside(grid, direction, height) else false } ?: true
    }

    private fun Point.viewingDistance(grid: IntGrid, direction: Point, height: Int): Int {
        val next = this + direction
        return grid.getOrNull(next)?.let { if (it < height) 1 + next.viewingDistance(grid, direction, height) else 1 } ?: 0
    }

    override fun part1(input: String) = input.toDigitGrid().let { grid ->
        grid.entries.count { (tree, height) ->
            cardinal.any { direction -> tree.canLookOutside(grid, direction, height) }
        }
    }

    override fun part2(input: String) = input.toDigitGrid().let { grid ->
        grid.entries.maxOf { (tree, height) ->
            cardinal.productOf { direction -> tree.viewingDistance(grid, direction, height) }
        }
    }
}