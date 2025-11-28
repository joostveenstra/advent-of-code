package year2019

import framework.Context
import framework.Day
import util.frequencies

class Day04(context: Context) : Day by context {
    val range = input.split('-').let { (min, max) -> min.toInt()..max.toInt() }.map { it.toString() }

    fun String.increasing() = zipWithNext().all { (a, b) -> a <= b }
    fun String.adjacent() = zipWithNext().any { (a, b) -> a == b }
    fun String.strictlyAdjacent() = frequencies().any { it.value == 2 }
    
    val increasing = range.filter { it.increasing() }

    fun part1() = increasing.count { it.adjacent() }
    fun part2() = increasing.count { it.strictlyAdjacent() }
}