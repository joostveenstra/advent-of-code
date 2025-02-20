package year2018

import framework.Context
import framework.Day

class Day05(context: Context) : Day by context {
    infix fun Char.reactsWith(other: Char) = this.lowercaseChar() == other.lowercaseChar() && (this != other)

    fun String.react(): Int {
        val left = toMutableList()
        val right = mutableListOf<Char>()

        while (left.isNotEmpty()) {
            if (right.isEmpty()) right.add(left.removeLast())
            else if (left.last() reactsWith right.last()) {
                left.removeLast()
                right.removeLast()
            } else right.add(left.removeLast())
        }

        return right.size
    }

    fun part1() = input.react()
    fun part2() = ('a'..'z').minOf { type -> input.filter { it.lowercaseChar() != type }.react() }
}