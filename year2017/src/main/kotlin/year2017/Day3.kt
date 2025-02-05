package year2017

import framework.Day
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.plus
import util.ORIGIN
import util.Point
import util.allNeighbours
import util.manhattan

object Day3 : Day {
    private fun Int.toPosition(): Point {
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

    override fun part1(input: String) = input.toInt().toPosition().manhattan

    override fun part2(input: String): Int {
        val target = input.toInt()
        tailrec fun find(n: Int, memory: PersistentMap<Point, Int>): Int {
            val point = n.toPosition()
            val sum = point.allNeighbours.sumOf { memory.getOrDefault(it, 0) }
            return if (sum > target) sum else find(n + 1, memory + (point to sum))
        }
        return find(2, persistentHashMapOf(ORIGIN to 1))
    }
}