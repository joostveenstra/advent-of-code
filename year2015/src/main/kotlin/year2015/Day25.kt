package year2015

import framework.Context
import framework.Day
import util.Point
import util.allInts

class Day25(context: Context) : Day by context {
    val position = input.allInts().toList().let { (y, x) -> Point(x, y) }

    fun Point.diagonalIndex(): Int {
        val n = x + y - 2
        val sum = (n * (n + 1)) / 2
        return sum + x - 1
    }

    fun Int.code(): Int {
        val index = toBigInteger()
        val start = 20151125.toBigInteger()
        val factor = 252533.toBigInteger()
        val divisor = 33554393.toBigInteger()
        return ((start * factor.modPow(index, divisor)) % divisor).toInt()
    }

    fun part1() = position.diagonalIndex().code()
}