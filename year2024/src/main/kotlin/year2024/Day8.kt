package year2024

import framework.Day
import util.*

object Day8 : Day {
    private fun parse(input: String) = with(input.toCharGrid()) {
        val antennas = entries
            .filter { it.second != '.' }
            .groupBy({ it.second }, { it.first })
            .values
        this to antennas
    }

    private fun CharGrid.countAntiNodes(antennas: Collection<List<Point>>, antiNodes: (Point, Point) -> Sequence<Point>) =
        with(asMutableIntGrid()) {
            antennas.forEach { frequency ->
                frequency.permPairs().forEach { (a, b) ->
                    antiNodes(b, b - a).forEach { this[it] = 1 }
                }
            }
            sum()
        }

    override fun part1(input: String) = parse(input).let { (grid, antennas) ->
        grid.countAntiNodes(antennas) { node, diff ->
            sequenceOf(node + diff).filter { it in grid }
        }
    }

    override fun part2(input: String) = parse(input).let { (grid, antennas) ->
        grid.countAntiNodes(antennas) { node, diff ->
            generateSequence(node) { it + diff }.takeWhile { it in grid }
        }
    }
}