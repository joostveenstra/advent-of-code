package year2015

import framework.Day

object Day11 : Day {
    private fun String.check(): Boolean =
        asIterable().windowed(3).any { (a, b, c) -> b - a == 1 && c - b == 1 }
                && none { it == 'i' || it == 'o' || it == 'l' }
                && asIterable().zipWithNext().filter { (a, b) -> a == b }.toSet().size >= 2

    private fun String.increment(): String {
        val builder = StringBuilder(this)
        for (i in indices.reversed()) {
            val char = this[i]
            if (char == 'z') {
                builder[i] = 'a'
            } else {
                builder[i] = char + 1
                break
            }
        }
        return builder.toString()
    }

    private fun String.next() = generateSequence(this) { it.increment() }.drop(1).first { it.check() }

    override fun part1(input: String) = input.next()

    override fun part2(input: String) = input.next().next()
}