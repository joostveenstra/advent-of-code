package year2021

import framework.Context
import framework.Day
import util.*

class Day11(context: Context) : Day by context {
    val initial = input.toDigitGrid()

    fun IntGrid.simulate() = sequence {
        val cave = toMutableGrid()
        val queue = dequeOf<Point>()
        
        fun increaseEnergy(position: Point) {
            if (cave[position] < 9) {
                cave[position] += 1
            } else {
                queue.add(position)
                cave[position] = 0
            }
        }
        
        while (true) {
            points.forEach(::increaseEnergy)

            queue.drain { position ->
                position
                    .allAdjacent()
                    .filter { cave[it] != 0 }
                    .forEach(::increaseEnergy)
            }

            yield(cave.count { it == 0 })
        }
    }

    fun part1() = initial.simulate().take(100).sum()
    fun part2() = initial.simulate().indexOfFirst { it == 100 } + 1
}