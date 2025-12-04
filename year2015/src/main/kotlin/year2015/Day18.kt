package year2015

import framework.Context
import framework.Day
import util.*

class Day18(context: Context) : Day by context {
    val grid = lines.toGrid { it == '#' }
    val corners = listOf(
        Point(0, 0),
        Point(grid.width - 1, 0),
        Point(0, grid.height - 1),
        Point(grid.width - 1, grid.height - 1)
    )

    fun BooleanGrid.cornersOn() = toMutableGrid().apply { corners.forEach { enable(it) } }

    fun BooleanGrid.step(): BooleanGrid {
        val next = asMutableBooleanGrid()
        points.filter { p ->
            val on = get(p)
            val neighboursOn = p.allAdjacentElements().count { it }
            neighboursOn == 3 || on && neighboursOn == 2
        }.forEach { next.enable(it) }
        return next
    }

    fun BooleanGrid.animate(step: (BooleanGrid) -> BooleanGrid) = generateSequence(this, step).nth(height).count { it }

    fun part1() = grid.animate { it.step() }
    fun part2() = grid.animate { it.step().cornersOn() }
}