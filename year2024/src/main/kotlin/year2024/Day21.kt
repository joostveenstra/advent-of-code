package year2024

import framework.Context
import framework.Day
import util.Point
import util.entries
import util.minus
import util.toCharGrid

class Day21(context: Context) : Day by context {
    val numeric = listOf("789", "456", "123", " 0A").toCharGrid().entries.toMap()
    val directional = listOf(" ^A", "<v>").toCharGrid().entries.toMap()
    val paths = numeric.paths() + directional.paths()

    fun Map<Point, Char>.paths() = keys
        .flatMap { from -> keys.map { to -> from to to } }
        .associate { (from, to) -> (this[from] to this[to]) to shortestPath(from, to) }

    fun Map<Point, Char>.shortestPath(from: Point, to: Point): String {
        val (dx, dy) = to - from
        val horizontal = if (dx > 0) ">".repeat(dx) else "<".repeat(-dx)
        val vertical = if (dy > 0) "v".repeat(dy) else "^".repeat(-dy)
        return if (dx > 0 && this[Point(from.x, to.y)] != ' ' || dx < 0 && this[Point(to.x, from.y)] == ' ') {
            vertical + horizontal
        } else {
            horizontal + vertical
        } + 'A'
    }

    val cache = mutableMapOf<Pair<String, Int>, Long>()
    fun String.buttonPresses(depth: Int): Long = cache.getOrPut(this to depth) {
        if (depth == 0) length.toLong()
        else "A$this".zipWithNext().sumOf { path -> paths.getValue(path).buttonPresses(depth - 1) }
    }

    fun complexities(depth: Int) = lines.sumOf { it.buttonPresses(depth) * it.take(3).toInt() }

    fun part1() = complexities(3)
    fun part2() = complexities(26)
}