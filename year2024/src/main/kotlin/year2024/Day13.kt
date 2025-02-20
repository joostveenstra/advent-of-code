package year2024

import framework.Context
import framework.Day
import util.allLongs

class Day13(context: Context) : Day by context {
    data class ClawMachine(val ax: Long, val ay: Long, val bx: Long, val by: Long, val px: Long, val py: Long)

    val claws = input.allLongs().chunked(6).map { ClawMachine(it[0], it[1], it[2], it[3], it[4], it[5]) }

    fun ClawMachine.correct(n: Long) = copy(px = px + n, py = py + n)

    fun ClawMachine.play(): Long {
        val det = ax * by - ay * bx
        val a = (by * px - bx * py) / det
        val b = (ax * py - ay * px) / det
        return if (ax * a + bx * b == px && ay * a + by * b == py)
            3L * a + b
        else 0L
    }

    fun part1() = claws.sumOf { it.play() }
    fun part2() = claws.sumOf { it.correct(10000000000000).play() }
}