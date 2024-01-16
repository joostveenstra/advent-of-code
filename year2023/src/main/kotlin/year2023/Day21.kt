package year2023

import framework.Day
import util.Grid
import util.Point
import util.dequeOf
import util.toCharGrid

object Day21 : Day<Long> {
    override fun part1(input: String) = input.toCharGrid().countSteps(64).values.count { it % 2 == 0 }.toLong()

    override fun part2(input: String): Long {
        val grid = input.toCharGrid()
        val steps = grid.countSteps()
        val oddCorners = steps.count { it.value % 2 == 1 && it.value > 65 }.toLong()
        val evenCorners = steps.count { it.value % 2 == 0 && it.value > 65 }.toLong()
        val evenBlock = steps.values.count { it % 2 == 0 }.toLong()
        val oddBlock = steps.values.count { it % 2 == 1 }.toLong()
        val n = ((26501365L - (grid.height / 2)) / grid.height)

        val even = n * n
        val odd = (n + 1) * (n + 1)
        return odd * oddBlock + even * evenBlock - (n + 1) * oddCorners + n * evenCorners
    }

    private fun Grid<Char>.countSteps(max: Int = height): Map<Point, Int> {
        val start = find('S')
        val queue = dequeOf(start to 0)
        val seen = mutableMapOf(start to 0)

        while (queue.isNotEmpty()) {
            val (position, steps) = queue.removeFirst()
            if (steps <= max) {
                val nextSteps = steps + 1
                position.cardinalNeighbours.filter { it !in seen && it in this && get(it) != '#' }.forEach { next ->
                    seen[next] = nextSteps
                    queue.addLast(next to nextSteps)
                }
            }
        }

        return seen
    }
}