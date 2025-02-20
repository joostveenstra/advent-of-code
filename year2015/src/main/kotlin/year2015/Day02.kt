package year2015

import framework.Context
import framework.Day

class Day02(context: Context) : Day by context {
    val boxes = lines.map { l -> l.split('x').map { it.toInt() } }

    fun part1() = boxes.sumOf { (l, w, h) ->
        val sides = listOf(l * w, w * h, h * l)
        2 * sides.sum() + sides.min()
    }

    fun part2() = boxes.sumOf { (l, w, h) ->
        val sides = listOf(l + w, w + h, h + l)
        2 * sides.min() + (l * w * h)
    }
}