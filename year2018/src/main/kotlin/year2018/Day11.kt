package year2018

import framework.Context
import framework.Day

class Day11(context: Context) : Day by context {
    data class Square(val x: Int, val y: Int, val size: Int, val sum: Int)

    fun Int.toFuelCells() = Array(301) { x ->
        IntArray(301) { y ->
            ((x + 10) * y + this) * (x + 10) / 100 % 10 - 5
        }
    }

    fun Array<IntArray>.toSummedAreaTable() = apply {
        for (x in 1..300) for (y in 1..300) {
            this[x][y] = this[x][y] + this[x - 1][y] + this[x][y - 1] - this[x - 1][y - 1]
        }
    }

    fun Array<IntArray>.toSquares(size: Int) = run {
        val sat = this
        sequence {
            for (x in size..300) for (y in size..300) {
                val sum = sat[x][y] - sat[x - size][y] - sat[x][y - size] + sat[x - size][y - size]
                yield(Square(x, y, size, sum))
            }
        }
    }

    fun part1(): String {
        val sat = input.toInt().toFuelCells().toSummedAreaTable()
        val squares = sat.toSquares(3)
        val (x, y, size) = squares.maxBy { it.sum }
        return listOf(x - size + 1, y - size + 1).joinToString(",")
    }

    fun part2(): String {
        val sat = input.toInt().toFuelCells().toSummedAreaTable()
        val squares = (1..300).asSequence().flatMap { size -> sat.toSquares(size) }
        val (x, y, size) = squares.maxBy { it.sum }
        return listOf(x - size + 1, y - size + 1, size).joinToString(",")
    }
}