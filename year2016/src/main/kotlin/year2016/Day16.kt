package year2016

import framework.Context
import framework.Day

class Day16(context: Context) : Day by context {
    val goal = if (isExample) 20 else 272

    fun String.checksum() = chunkedSequence(length.takeLowestOneBit()).joinToString("") { chunk ->
        if (chunk.count { it == '1' } % 2 == 0) "1" else "0"
    }

    fun String.fill(goal: Int): String {
        fun step(a: String) = a + '0' + a.reversed().asIterable().joinToString("") { if (it == '1') "0" else "1" }
        return generateSequence(this, ::step).dropWhile { it.length < goal }.first().take(goal)
    }

    fun part1() = input.fill(goal).checksum()
    fun part2() = input.fill(35651584).checksum()
}