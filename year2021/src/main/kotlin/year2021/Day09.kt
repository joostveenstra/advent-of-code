package year2021

import framework.Context
import framework.Day
import util.*

class Day09(context: Context) : Day by context {
    val grid = input.toDigitGrid()
    val lowPoints = grid.findLowPoints()

    fun IntGrid.findLowPoints() = points.filter { p ->
        val height = get(p)
        p.cardinalElements().all { it > height }
    }

    fun IntGrid.getBasinSize(position: Point): Int {
        val queue = dequeOf(position)
        val visited = mutableSetOf(position)

        queue.drain { p ->
            p.cardinal().filter { it !in visited && get(it) < 9 }.forEach { next ->
                visited.add(next)
                queue.add(next)
            }
        }

        return visited.size
    }

    fun part1() = lowPoints.sumOf { grid[it] + 1 }
    fun part2() = lowPoints.map { grid.getBasinSize(it) }.sorted().takeLast(3).product()
}