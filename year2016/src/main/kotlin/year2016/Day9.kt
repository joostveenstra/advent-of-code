package year2016

import framework.Day
import util.match

object Day9 : Day {
    private val regex = "\\((\\d+)x(\\d+)\\)(.*)".toRegex()

    private fun String.decompress(version: Int, size: Long = 0): Long =
        if (isEmpty()) size else match(regex)
            ?.let { (amount, repeat, rest) ->
                val count = if (version == 1) amount.toLong() else rest.take(amount.toInt()).decompress(version)
                rest.drop(amount.toInt()).decompress(version, size + repeat.toLong() * count)
            }
            ?: drop(1).decompress(version, size + 1)

    override fun part1(input: String) = input.decompress(1)

    override fun part2(input: String) = input.decompress(2)
}