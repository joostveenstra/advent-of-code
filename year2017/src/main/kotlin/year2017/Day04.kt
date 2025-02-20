package year2017

import framework.Context
import framework.Day
import util.allDistinct

class Day04(context: Context) : Day by context {
    fun part1() = lines.count { line ->
        val words = line.split(' ')
        words.allDistinct()
    }

    fun part2() = lines.count { line ->
        val words = line.split(' ').map { it.asIterable().sorted() }
        words.allDistinct()
    }
}