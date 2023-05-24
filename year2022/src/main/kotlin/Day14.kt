import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toPersistentHashSet
import kotlin.math.max
import kotlin.math.min

typealias Cave = PersistentSet<Point>

object Day14 : Day<Int> {
    private val source = Point(500, 0)
    private val directions = listOf(
        Point(0, 1),
        Point(-1, 1),
        Point(1, 1)
    )

    private fun String.toEmptyCave(): Cave = lines()
        .flatMap { it.split(" -> ").map(Point::of).zipWithNext { a, b -> a lineTo b } }
        .flatten()
        .toPersistentHashSet()

    private fun Cave.floor() = maxOf { it.y } + 1

    private infix fun Point.lineTo(other: Point): List<Point> = buildList {
        for (x in min(x, other.x)..max(x, other.x))
            for (y in min(y, other.y)..max(y, other.y))
                add(Point(x, y))
    }

    private tailrec fun Point.fall(cave: Cave, floor: Int): Point {
        val candidates = directions.map { this + it }.filterNot { it in cave }
        return if (y == floor || candidates.isEmpty()) this else candidates.first().fall(cave, floor)
    }

    private tailrec fun Cave.dropSand(floor: Int, isEnd: (Point) -> Boolean): Int {
        val unit = source.fall(this, floor)
        return if (isEnd(unit)) size else (this + unit).dropSand(floor, isEnd)
    }

    override fun part1(input: String) = input.toEmptyCave().let { cave ->
        val floor = cave.floor()
        cave.dropSand(floor) { it.y == floor } - cave.size
    }

    override fun part2(input: String) = input.toEmptyCave().let { cave ->
        val floor = cave.floor()
        cave.dropSand(floor) { it == source } - cave.size + 1
    }
}