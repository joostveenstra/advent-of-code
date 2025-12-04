package year2022

import framework.Context
import framework.Day
import util.*

class Day08(context: Context) : Day by context {
    val grid = input.toDigitGrid()

    fun Point.canLookOutside(direction: Point, height: Int): Boolean {
        val next = this + direction
        return grid.getOrNull(next)?.let { if (it < height) next.canLookOutside(direction, height) else false } ?: true
    }

    fun Point.viewingDistance(direction: Point, height: Int): Int {
        val next = this + direction
        return grid.getOrNull(next)?.let { if (it < height) 1 + next.viewingDistance(direction, height) else 1 } ?: 0
    }

    fun part1() = grid.entries.count { (tree, height) ->
        dCardinal.any { direction -> tree.canLookOutside(direction, height) }
    }


    fun part2() = grid.entries.maxOf { (tree, height) ->
        dCardinal.productOf { direction -> tree.viewingDistance(direction, height) }
    }
}