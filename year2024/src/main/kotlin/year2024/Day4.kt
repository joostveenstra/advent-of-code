package year2024

import framework.Day
import util.*

object Day4 : Day {
    private const val XMAS = "XMAS"
    private val X_MAS = setOf("MMSS", "SSMM", "MSSM", "SMMS")

    private tailrec fun CharGrid.containsWordAt(position: Point, direction: Point, word: String): Boolean = when {
        word.isEmpty() -> true
        word.first() != getOrNull(position) -> false
        else -> containsWordAt(position + direction, direction, word.drop(1))
    }

    override fun part1(input: String) = with(input.toCharGrid()) {
        findPoints('X').sumOf { x ->
            allDirections.count { d -> containsWordAt(x, d, XMAS) }
        }
    }

    override fun part2(input: String) = with(input.toCharGrid()) {
        findPoints('A').count { a ->
            a.diagonalNeighbours.mapNotNull(::getOrNull).joinToString("") in X_MAS
        }
    }
}