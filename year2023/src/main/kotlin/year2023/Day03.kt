package year2023

import framework.Context
import framework.Day
import util.Point
import util.allNeighbours

class Day03(context: Context) : Day by context {
    val symbols = buildMap {
        lines.forEachIndexed { y, row ->
            row.forEachIndexed { x, char ->
                if (char != '.' && !char.isDigit()) put(Point(x, y), char)
            }
        }
    }
    val parts = buildMap {
        lines.forEachIndexed { y, row ->
            val parts = "\\d+".toRegex().findAll(row).map { Point(it.range.first, y) to it.value }
            putAll(parts)
        }
    }

    fun Point.adjacent(length: Int): Set<Point> = (x..<x + length).flatMap { x -> Point(x, y).allNeighbours }.toSet()

    fun part1() = parts
        .filter { (start, value) -> start.adjacent(value.length).any { it in symbols } }
        .values
        .sumOf { it.toInt() }


    val adjacent = parts.mapKeys { (k, v) -> k.adjacent(v.length) }

    fun part2() = symbols
        .filterValues { it == '*' }
        .keys
        .map { gear -> adjacent.filterKeys { gear in it }.values }
        .filter { it.size == 2 }
        .sumOf { it.first().toInt() * it.last().toInt() }
}