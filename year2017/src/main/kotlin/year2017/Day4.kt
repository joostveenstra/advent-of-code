package year2017

import framework.Day

object Day4 : Day<Int> {
    override fun part1(input: String) = input.lines().count { line ->
        val words = line.split(' ')
        words.size == words.distinct().size
    }

    override fun part2(input: String) = input.lines().count { line ->
        val words = line.split(' ').map { it.asIterable().sorted() }
        words.size == words.distinct().size
    }
}