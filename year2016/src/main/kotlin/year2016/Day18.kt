package year2016

import framework.Context
import framework.Day
import java.math.BigInteger

class Day18(context: Context) : Day by context {
    fun String.countSafeRows(rows: Int): Int {
        val initial = map { if (it == '^') '1' else '0' }.joinToString("").toBigInteger(2)
        fun step(row: BigInteger) = (row shl 1).clearBit(length) xor (row shr 1)
        return generateSequence(initial, ::step).take(rows).sumOf { length - it.bitCount() }
    }

    fun part1() = input.countSafeRows(if (isExample) 10 else 40)
    fun part2() = input.countSafeRows(400000)
}