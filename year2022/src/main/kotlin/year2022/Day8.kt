package year2022

import framework.Day
import util.Direction
import util.Point
import util.productOf

object Day8 : Day<Int> {
    private fun String.toGrid() = buildMap {
        lines().forEachIndexed { y, row ->
            row.forEachIndexed { x, char ->
                put(Point(x, y), char.digitToInt())
            }
        }
    }

    private fun Point.canLookOutside(grid: Map<Point, Int>, direction: Point, height: Int): Boolean {
        val next = this + direction
        return grid[next]?.let { if (it < height) next.canLookOutside(grid, direction, height) else false } ?: true
    }

    private fun Point.viewingDistance(grid: Map<Point, Int>, direction: Point, height: Int): Int {
        val next = this + direction
        return grid[next]?.let { if (it < height) 1 + next.viewingDistance(grid, direction, height) else 1 } ?: 0
    }

    override fun part1(input: String) = input.toGrid().let { grid ->
        grid.count { (tree, height) ->
            Direction.cardinal.any { direction -> tree.canLookOutside(grid, direction, height) }
        }
    }

    override fun part2(input: String) = input.toGrid().let { grid ->
        grid.maxOf { (tree, height) ->
            Direction.cardinal.productOf { direction -> tree.viewingDistance(grid, direction, height) }
        }
    }
}