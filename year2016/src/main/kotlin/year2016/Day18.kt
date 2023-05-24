package year2016

import framework.Day
import java.math.BigInteger

object Day18 : Day<Int> {
    private fun String.toNumberOfRows() = if (length == 10) 10 else 40

    private fun String.countSafeRows(rows: Int): Int {
        val initial = map { if (it == '^') '1' else '0' }.joinToString("").toBigInteger(2)
        fun step(row: BigInteger) = (row shl 1).clearBit(length) xor (row shr 1)
        return generateSequence(initial, ::step).take(rows).sumOf { length - it.bitCount() }
    }

    override fun part1(input: String) = input.countSafeRows(input.toNumberOfRows())

    override fun part2(input: String) = input.countSafeRows(400000)
}