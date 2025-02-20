package year2024

import framework.Context
import framework.Day
import util.*

class Day04(context: Context) : Day by context {
    val xmas = setOf("MMSS", "SSMM", "MSSM", "SMMS")
    val grid = input.toCharGrid()

    tailrec fun CharGrid.containsWordAt(position: Point, direction: Point, word: String): Boolean = when {
        word.isEmpty() -> true
        word.first() != getOrNull(position) -> false
        else -> containsWordAt(position + direction, direction, word.drop(1))
    }

    fun part1() = grid.findPoints('X').sumOf { x ->
        allDirections.count { d -> grid.containsWordAt(x, d, "XMAS") }
    }

    fun part2() = grid.findPoints('A').count { a ->
        a.diagonalNeighbours.mapNotNull(grid::getOrNull).joinToString("") in xmas
    }
}