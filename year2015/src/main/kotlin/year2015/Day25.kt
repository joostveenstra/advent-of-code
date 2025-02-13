package year2015

import framework.Day
import util.Point
import util.allInts

object Day25 : Day {
    private fun String.toPosition() = allInts().toList().let { (y, x) -> Point(x, y) }

    private fun Point.diagonalIndex(): Int {
        val n = x + y - 2
        val sum = (n * (n + 1)) / 2
        return sum + x - 1
    }

    private fun Int.code(): Int {
        val index = toBigInteger()
        val start = 20151125.toBigInteger()
        val factor = 252533.toBigInteger()
        val divisor = 33554393.toBigInteger()
        return ((start * factor.modPow(index, divisor)) % divisor).toInt()
    }

    override fun part1(input: String) = input.toPosition().diagonalIndex().code()

    override fun part2(input: String) = 0
}