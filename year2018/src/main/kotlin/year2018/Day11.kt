package year2018

import framework.Context
import framework.Day

class Day11(context: Context) : Day by context {
    data class Square(val x: Int, val y: Int, val size: Int, val power: Int)

    fun String.toSummedAreaTable(): Array<IntArray> {
        val gridSerialNumber = toInt()
        val sat = Array(301) { IntArray(301) }
        for (x in 1..300) for (y in 1..300) {
            val rackId = x + 10
            val power = (rackId * y + gridSerialNumber) * rackId / 100 % 10 - 5
            sat[x][y] = power + sat[x - 1][y] + sat[x][y - 1] - sat[x - 1][y - 1]
        }
        return sat
    }

    fun Array<IntArray>.square(size: Int) = let { sat ->
        var maxPower = 0
        var maxX = 0
        var maxY = 0
        for (x in size..300) for (y in size..300) {
            val power = sat[x][y] - sat[x - size][y] - sat[x][y - size] + sat[x - size][y - size]
            if (power > maxPower) {
                maxPower = power
                maxX = x - size + 1
                maxY = y - size + 1
            }
        }
        Square(maxX, maxY, size, maxPower)
    }

    val sat = input.toSummedAreaTable()
    val squares = (1..300).map { size -> sat.square(size) }

    fun part1() = squares.first { it.size == 3 }.let { (x, y) -> "$x,$y" }
    fun part2() = squares.maxBy { it.power }.let { (x, y, size) -> "$x,$y,$size" }
}