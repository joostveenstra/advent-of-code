package year2015

import framework.Context
import framework.Day

class Day11(context: Context) : Day by context {
    fun String.increment(): String {
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

    fun String.check(): Boolean =
        asIterable().windowed(3).any { (a, b, c) -> b - a == 1 && c - b == 1 }
                && none { it == 'i' || it == 'o' || it == 'l' }
                && asIterable().zipWithNext().filter { (a, b) -> a == b }.toSet().size >= 2

    fun String.next() = generateSequence(this) { it.increment() }.drop(1).first { it.check() }

    val next = input.next()

    fun part1() = next
    fun part2() = next.next()
}