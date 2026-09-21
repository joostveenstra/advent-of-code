package year2021

import framework.Context
import framework.Day
import util.*

class Day04(context: Context) : Day by context {
    data class Board(val turn: Int, val score: Int)

    val chunks = input.split(EMPTY_LINE).map { it.allInts().toList() }
    val numbers = chunks.first().withIndex()
    val numberToTurn = numbers.associate { (i, n) -> n to i }
    val turnToNumber = numbers.associate { (i, n) -> i to n }
    val boards = chunks.drop(1).map { b ->
        with(Grid(5, 5, b)) {
            val turn = (rowsValues + columnsValues).minOf { it.maxOf(numberToTurn::getValue) }
            val unmarked = filter { numberToTurn.getValue(it) > turn }.sum()
            val number = turnToNumber.getValue(turn)
            Board(turn, unmarked * number)
        }
    }

    fun part1() = boards.minBy { it.turn }.score
    fun part2() = boards.maxBy { it.turn }.score
}