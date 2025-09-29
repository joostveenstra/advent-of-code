package year2022

import framework.Context
import framework.Day
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toPersistentHashSet
import util.Point
import util.plus
import util.toPoint
import kotlin.math.max
import kotlin.math.min

class Day14(context: Context) : Day by context {
    val source = Point(500, 0)
    val directions = listOf(
        Point(0, 1),
        Point(-1, 1),
        Point(1, 1)
    )

    val emptyCave = lines
        .flatMap { line -> line.split(" -> ").map { it.toPoint() }.zipWithNext { a, b -> a lineTo b } }
        .flatten()
        .toPersistentHashSet()

    fun Cave.floor() = maxOf { it.y } + 1

    infix fun Point.lineTo(other: Point): List<Point> = buildList {
        for (x in min(x, other.x)..max(x, other.x))
            for (y in min(y, other.y)..max(y, other.y))
                add(Point(x, y))
    }

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