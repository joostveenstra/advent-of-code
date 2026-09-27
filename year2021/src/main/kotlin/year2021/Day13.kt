package year2021

import framework.Context
import framework.Day
import util.Point
import util.mutableGrid
import util.rowsValues
import util.toPoint

class Day13(context: Context) : Day by context {
    sealed interface Fold
    class Horizontal(val y: Int) : Fold
    class Vertical(val x: Int) : Fold

    val dots = lines.takeWhile { it.isNotBlank() }.map { it.toPoint() }
    val folds = lines.takeLastWhile { it.isNotBlank() }.map {
        val (direction, position) = it.split("=")
        when (direction.last()) {
            'y' -> Horizontal(position.toInt())
            else -> Vertical(position.toInt())
        }
    }

    fun part1(): Int {
        val fold = folds.first()

        fun Point.apply(fold: Fold) = when (fold) {
            is Horizontal if (y >= fold.y) -> Point(x, 2 * fold.y - y)
            is Vertical if (x >= fold.x) -> Point(2 * fold.x - x, y)
            else -> this
        }

        return dots.map { it.apply(fold) }.toSet().size
    }

    fun part2(): String {
        val width = folds.filterIsInstance<Vertical>().last().x
        val height = folds.filterIsInstance<Horizontal>().last().y
        val grid = mutableGrid(width, height) { '.' }

        fun Int.fold(max: Int): Int {
            val period = 2 * (max + 1)
            val remainder = this % period
            return if (remainder > max) period - remainder - 2 else remainder
        }

        dots.forEach { (x, y) -> grid[Point(x.fold(width), y.fold(height))] = '#' }

        return grid.rowsValues.joinToString("\n") { it.joinToString("") }
    }
}