package year2019

import framework.Context
import framework.Day
import util.*

class Day03(context: Context) : Day by context {
    val wires = lines.map { l ->
        l.split(',').map { it.first().toDirection() to it.drop(1).toInt() }
            .scan(listOf(ORIGIN)) { positions, (direction, steps) ->
                (1..steps).map { positions.last() + direction * it }
            }.drop(1).flatten()
    }
    val intersections = wires[0].intersect(wires[1].toSet())

    fun part1() = intersections.minOf { it.manhattan }
    fun part2() = intersections.minOf { i -> wires.sumOf { it.indexOf(i) + 1 } }
}