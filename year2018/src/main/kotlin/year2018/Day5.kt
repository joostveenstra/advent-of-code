package year2018

import framework.Day

object Day5 : Day {
    private infix fun Char.reactsWith(other: Char) = this.lowercaseChar() == other.lowercaseChar() && (this != other)

    private fun String.react(): Int {
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

    override fun part1(input: String) = input.react()

    override fun part2(input: String) = ('a'..'z').minOf { type -> input.filter { it.lowercaseChar() != type }.react() }
}