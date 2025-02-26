package year2023

import framework.Context
import framework.Day
import util.*

class Day21(context: Context) : Day by context {
    val grid = input.toCharGrid()
    val steps = if (isExample) 6 else 64

    fun CharGrid.countPlots(max: Int = height): Map<Point, Int> {
        val start = findPoint('S')
        val queue = dequeOf(start to 0)
        val seen = mutableMapOf(start to 0)

        queue.drain { (position, steps) ->
            if (steps <= max) {
                val nextSteps = steps + 1
                position.cardinalNeighbours.filter { it !in seen && it in this && get(it) != '#' }.forEach { next ->
                    seen[next] = nextSteps
                    queue.add(next to nextSteps)
                }
            }
        }

        return seen
    }

    fun part1() = grid.countPlots(steps).values.count { it % 2 == 0 }.toLong()
    fun part2() = run {
        val plots = grid.countPlots()
        val oddCorners = plots.count { it.value % 2 == 1 && it.value > 65 }.toLong()
        val evenCorners = plots.count { it.value % 2 == 0 && it.value > 65 }.toLong()
        val evenBlock = plots.values.count { it % 2 == 0 }.toLong()
        val oddBlock = plots.values.count { it % 2 == 1 }.toLong()
        val n = ((26501365L - (grid.height / 2)) / grid.height)
        val even = n * n
        val odd = (n + 1) * (n + 1)

        odd * oddBlock + even * evenBlock - (n + 1) * oddCorners + n * evenCorners
    }
}