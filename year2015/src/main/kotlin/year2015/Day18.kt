package year2015

import framework.Context
import framework.Day
import util.Point
import util.allNeighbours
import util.nth

class Day18(context: Context) : Day by context {
    data class Grid(val length: Int, val points: Set<Point>)

    val grid = lines.let {
        Grid(it.size, buildSet {
            it.forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    if (char == '#') add(Point(x, y))
                }
            }
        })
    }

    fun Grid.corners() = listOf(
        Point(0, 0),
        Point(length - 1, 0),
        Point(0, length - 1),
        Point(length - 1, length - 1)
    )

    fun Grid.cornersOn() = Grid(length, points + corners())

    fun Grid.step(): Grid = Grid(length, buildSet {
        val range = 0 until length
        for (x in range) for (y in range) Point(x, y)
            .takeIf { p ->
                val on = p in points
                val neighboursOn = p.allNeighbours.count { it in points }
                neighboursOn == 3 || on && neighboursOn == 2
            }?.let { add(it) }
    })

    fun Grid.animate(step: (Grid) -> Grid) = generateSequence(this, step).nth(length).points.size

    fun part1() = grid.animate { it.step() }
    fun part2() = grid.animate { it.step().cornersOn() }
}