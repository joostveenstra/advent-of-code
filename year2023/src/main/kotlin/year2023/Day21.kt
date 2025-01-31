package year2023

import framework.Day
import util.*

object Day21 : Day {
    override fun part1(input: String) = input.toCharGrid().countSteps(64).values.count { it % 2 == 0 }.toLong()

    override fun part2(input: String) = with(input.toCharGrid()) {
        val steps = countSteps()
        val oddCorners = steps.count { it.value % 2 == 1 && it.value > 65 }.toLong()
        val evenCorners = steps.count { it.value % 2 == 0 && it.value > 65 }.toLong()
        val evenBlock = steps.values.count { it % 2 == 0 }.toLong()
        val oddBlock = steps.values.count { it % 2 == 1 }.toLong()
        val n = ((26501365L - (height / 2)) / height)

        val even = n * n
        val odd = (n + 1) * (n + 1)
        odd * oddBlock + even * evenBlock - (n + 1) * oddCorners + n * evenCorners
    }

    private fun CharGrid.countSteps(max: Int = height): Map<Point, Int> {
        val start = findPoint('S')
        val queue = dequeOf(start to 0)
        val seen = mutableMapOf(start to 0)

        queue.drain { (position, steps) ->
            if (steps <= max) {
                val nextSteps = steps + 1
                position.cardinalNeighbours.filter { it !in seen && it in this && get(it) != '#' }.forEach { next ->
                    seen[next] = nextSteps
                    queue += next to nextSteps
                }
            }
        }

        return seen
    }
}