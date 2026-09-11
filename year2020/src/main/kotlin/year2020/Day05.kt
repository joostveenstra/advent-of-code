package year2020

import framework.Context
import framework.Day
import util.toInt

class Day05(context: Context) : Day by context {
    val ids = lines.map { l ->
        l.fold(0) { id, c -> (id shl 1) or (c == 'B' || c == 'R').toInt() }
    }
    val xor = ids.reduce { acc, id -> acc xor id }

    fun part1() = ids.max()
    fun part2() = (ids.min()..ids.max()).fold(xor) { target, id -> target xor id }
}