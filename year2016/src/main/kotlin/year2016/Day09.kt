package year2016

import framework.Context
import framework.Day
import util.match

class Day09(context: Context) : Day by context {
    val regex = "\\((\\d+)x(\\d+)\\)(.*)".toRegex()

    fun String.decompress(version: Int, size: Long = 0): Long =
        if (isEmpty()) size else match(regex)
            ?.let { (amount, repeat, rest) ->
                val count = if (version == 1) amount.toLong() else rest.take(amount.toInt()).decompress(version)
                rest.drop(amount.toInt()).decompress(version, size + repeat.toLong() * count)
            }
            ?: drop(1).decompress(version, size + 1)

    fun part1() = input.decompress(1)
    fun part2() = input.decompress(2)
}