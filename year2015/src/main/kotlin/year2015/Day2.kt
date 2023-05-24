package year2015

import framework.Day

object Day2 : Day<Int> {
    private fun String.toBox() = split('x').map { it.toInt() }

    override fun part1(input: String) = input.lines().map { it.toBox() }.sumOf { (l, w, h) ->
        val sides = listOf(l * w, w * h, h * l)
        2 * sides.sum() + sides.min()
    }

    override fun part2(input: String) = input.lines().map { it.toBox() }.sumOf { (l, w, h) ->
        val sides = listOf(l + w, w + h, h + l)
        2 * sides.min() + (l * w * h)
    }
}