package year2017

import framework.Context
import framework.Day
import util.product

class Day10(context: Context) : Day by context {
    val size = if (isExample) 5 else 256

    fun List<Int>.knotHash(size: Int): List<Int> {
        val initial = (0 until size).toList() to 0
        val (numbers, position) = withIndex().fold(initial) { (numbers, position), (skip, length) ->
            val next = numbers.take(length).reversed() + numbers.drop(length)
            val offset = (length + skip) % size
            next.drop(offset) + next.take(offset) to (position - offset).mod(size)
        }
        return numbers.drop(position) + numbers.take(position)
    }

    fun part1(): Int {
        val lengths = input.split(',').map { it.toInt() }
        return lengths.knotHash(size).take(2).product()
    }

    fun part2(): String {
        val lengths = input.map { it.code } + listOf(17, 31, 73, 47, 23)
        val repeated = List(64) { lengths }.flatten()
        return repeated.knotHash(256).chunked(16).map { it.reduce(Int::xor).toByte() }.toByteArray().toHexString()
    }
}