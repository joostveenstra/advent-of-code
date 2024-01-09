package year2023

import framework.Day
import util.Point

object Day3 : Day<Int> {
    private fun parse(input: String) = with(input.lines()) {
        val symbols = buildMap {
            forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    if (char != '.' && !char.isDigit()) put(Point(x, y), char)
                }
            }
        }
        val parts = buildMap {
            forEachIndexed { y, row ->
                val parts = "\\d+".toRegex().findAll(row).map { Point(it.range.first, y) to it.value }
                putAll(parts)
            }
        }
        symbols to parts
    }

    private fun Point.adjacent(length: Int): Set<Point> = (x..<x + length).flatMap { x -> Point(x, y).neighbours }.toSet()

    override fun part1(input: String): Int {
        val (symbols, parts) = parse(input)
        return parts
            .filter { (start, value) -> start.adjacent(value.length).any { it in symbols } }
            .values
            .sumOf { it.toInt() }
    }

    override fun part2(input: String): Int {
        val (symbols, parts) = parse(input)
        val adjacent = parts.mapKeys { (k, v) -> k.adjacent(v.length) }
        return symbols
            .filterValues { it == '*' }
            .keys
            .map { gear -> adjacent.filterKeys { gear in it }.values }
            .filter { it.size == 2 }
            .sumOf { it.map(String::toInt).reduce(Int::times) }
    }
}