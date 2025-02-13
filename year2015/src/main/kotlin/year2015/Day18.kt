package year2015

import framework.Day
import util.Point
import util.allNeighbours
import util.nth

object Day18 : Day {
    data class Grid(val length: Int, val points: Set<Point>)

    // TODO: Convert to grid??
    private fun String.toGrid() = lines().let {
        Grid(it.size, buildSet {
            it.forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    if (char == '#') add(Point(x, y))
                }
            }
        })
    }

    private val Grid.corners get() = listOf(
        Point(0, 0),
        Point(length - 1, 0),
        Point(0, length - 1),
        Point(length - 1, length - 1)
    )

    private fun Grid.cornersOn() = Grid(length, points + corners)

    private fun Grid.step(): Grid = Grid(length, buildSet {
        val range = 0 until length
        for (x in range) for (y in range) Point(x, y)
            .takeIf { p ->
                val on = p in points
                val neighboursOn = p.allNeighbours.count { it in points }
                neighboursOn == 3 || on && neighboursOn == 2
            }?.let { add(it) }
    })

    private fun Grid.animate(step: (Grid) -> Grid) = generateSequence(this, step).nth(length).points.size

    override fun part1(input: String) = input.toGrid().animate { it.step() }

    override fun part2(input: String) = input.toGrid().animate { it.step().cornersOn() }
}