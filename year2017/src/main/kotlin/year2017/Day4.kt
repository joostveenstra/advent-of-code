package year2017

import framework.Day
import util.allDistinct

object Day4 : Day {
    override fun part1(input: String) = input.lines().count { line ->
        val words = line.split(' ')
        words.allDistinct()
    }

    override fun part2(input: String) = input.lines().count { line ->
        val words = line.split(' ').map { it.asIterable().sorted() }
        words.allDistinct()
    }
}