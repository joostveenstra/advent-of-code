package year2020

import framework.Context
import framework.Day
import util.toInt

class Day05(context: Context) : Day by context {
    val ids = lines.map { l ->
        l.fold(0) { id, c -> (id shl 1) or (c == 'B' || c == 'R').toInt() }
    }
    val min = ids.min()
    val max = ids.max()
    val xor = ids.reduce { acc, id -> acc xor id }

    fun part1() = max
    fun part2() = (min..max).fold(xor) { target, id -> target xor id }
}