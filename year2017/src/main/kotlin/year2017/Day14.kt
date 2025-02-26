package year2017

import framework.Context
import framework.Day
import util.Point
import util.cardinalNeighbours

class Day14(context: Context) : Day by context {
    fun String.knotHash(): List<Int> {
        val lengths = map { it.code } + listOf(17, 31, 73, 47, 23)
        val repeated = List(64) { lengths }.flatten()
        val initial = (0..255).toList() to 0
        val (numbers, position) = repeated.withIndex().fold(initial) { (numbers, position), (skip, length) ->
            val next = numbers.take(length).reversed() + numbers.drop(length)
            val offset = (length + skip) and 0xFF
            next.drop(offset) + next.take(offset) to ((position - offset) and 0xFF)
        }
        return (numbers.drop(position) + numbers.take(position)).chunked(16).map { it.reduce(Int::xor) }
    }

    fun String.toBinary() = (0..127).map { row ->
        "$this-$row".knotHash().joinToString("") { it.toString(2).padStart(8, '0') }
    }

    val binary = input.toBinary()

    fun part1() = binary.sumOf { row -> row.count { it == '1' } }
    fun part2(): Int {
        val used = binary.flatMapIndexed { y, row ->
            (0..127).mapNotNull { x -> if (row[x] == '1') Point(x, y) else null }
        }
        val cliques = used.fold(listOf<List<Point>>()) { groups, point ->
            val (other, linked) = groups.partition { group -> point.cardinalNeighbours.none { it in group } }
            other + listOf(linked.flatten() + point)
        }
        return cliques.size
    }
}