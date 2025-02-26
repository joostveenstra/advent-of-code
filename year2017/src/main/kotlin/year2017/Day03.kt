package year2017

import framework.Context
import framework.Day
import util.ORIGIN
import util.Point
import util.allNeighbours
import util.manhattan

class Day03(context: Context) : Day by context {
    val target = input.toInt()

    fun Int.toPosition(): Point {
        val n = generateSequence(1) { it + 2 }.dropWhile { it * it < this }.first()
        val base = this - (n - 2) * (n - 2) - 1
        val size = n - 1
        val half = size / 2
        val offset = base % size
        return when (base / size) {
            0 -> Point(half, half - 1 - offset)
            1 -> Point(half - 1 - offset, -half)
            2 -> Point(-half, offset + 1 - half)
            else -> Point(offset + 1 - half, half)
        }
    }

    fun part1() = target.toPosition().manhattan
    fun part2() = run {
        val memory = mutableMapOf(ORIGIN to 1)
        tailrec fun find(n: Int): Int {
            val point = n.toPosition()
            val sum = point.allNeighbours.sumOf { memory.getOrDefault(it, 0) }
            return if (sum > target) sum else {
                memory[point] = sum
                find(n + 1)
            }
        }
        find(2)
    }
}