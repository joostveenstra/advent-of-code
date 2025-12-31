package year2025

import framework.Context
import framework.Day
import util.*
import kotlin.math.max
import kotlin.math.min

class Day09(context: Context) : Day by context {
    data class Rectangle(val x: IntRange, val y: IntRange) {
        constructor(a: Point, b: Point) : this(
            min(a.x, b.x)..max(a.x, b.x),
            min(a.y, b.y)..max(a.y, b.y)
        )

        val area = x.size.toLong() * y.size

        fun inner() = Rectangle(
            x.first + 1..<x.last,
            y.first + 1..<y.last
        )

        infix fun overlaps(other: Rectangle) = x overlaps other.x && y overlaps other.y
    }

    val tiles = lines.map { it.toPoint() }
    val edges = (tiles + tiles.first()).zipWithNext { a, b -> Rectangle(a, b) }
    val rectangles = tiles.pairs().map { (a, b) -> Rectangle(a, b) }.sortedByDescending { it.area }

    fun part1() = rectangles.first().area
    fun part2() = rectangles.first { r ->
        val inner = r.inner()
        edges.none { it overlaps inner }
    }.area
}