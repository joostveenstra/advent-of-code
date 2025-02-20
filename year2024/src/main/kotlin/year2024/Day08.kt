package year2024

import framework.Context
import framework.Day
import util.*

class Day08(context: Context) : Day by context {
    val grid = input.toCharGrid()
    val antennas = grid.entries
        .filter { it.second != '.' }
        .groupBy({ it.second }, { it.first })
        .values

    fun countAntiNodes(antiNodes: (Point, Point) -> Sequence<Point>) = with(grid.asMutableIntGrid()) {
        antennas.forEach { frequency ->
            frequency.permPairs().forEach { (a, b) ->
                antiNodes(b, b - a).forEach { this[it] = 1 }
            }
        }
        sum()
    }

    fun part1() = countAntiNodes { node, diff ->
        sequenceOf(node + diff).filter { it in grid }
    }

    fun part2() = countAntiNodes { node, diff ->
        generateSequence(node) { it + diff }.takeWhile { it in grid }
    }
}