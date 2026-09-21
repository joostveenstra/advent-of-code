package year2022

import framework.Context
import framework.Day
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toPersistentHashSet
import util.*

class Day14(context: Context) : Day by context {
    val source = Point(500, 0)
    val directions = listOf(
        Point(0, 1),
        Point(-1, 1),
        Point(1, 1)
    )

    val emptyCave = lines
        .flatMap { l -> l.split(" -> ").map { it.toPoint() }.zipWithNext { a, b -> (a..b).allPoints() } }
        .flatten()
        .toPersistentHashSet()

    fun Cave.floor() = maxOf { it.y } + 1

    tailrec fun Point.fall(cave: Cave, floor: Int): Point {
        val candidates = directions.map { this + it }.filterNot { it in cave }
        return if (y == floor || candidates.isEmpty()) this else candidates.first().fall(cave, floor)
    }

    tailrec fun Cave.dropSand(floor: Int, isEnd: (Point) -> Boolean): Int {
        val unit = source.fall(this, floor)
        return if (isEnd(unit)) size else (this + unit).dropSand(floor, isEnd)
    }

    fun part1() = emptyCave.let { cave ->
        val floor = cave.floor()
        cave.dropSand(floor) { it.y == floor } - cave.size
    }

    fun part2() = emptyCave.let { cave ->
        val floor = cave.floor()
        cave.dropSand(floor) { it == source } - cave.size + 1
    }
}