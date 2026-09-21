package util

import util.Orientation.HORIZONTAL
import util.Orientation.VERTICAL
import kotlin.math.absoluteValue
import kotlin.math.sqrt

typealias Direction = Point

val ORIGIN = Point(0, 0)
val RIGHT = Point(1, 0)
val DOWN = Point(0, 1)
val LEFT = Point(-1, 0)
val UP = Point(0, -1)
val EAST = RIGHT
val SOUTH = DOWN
val WEST = LEFT
val NORTH = UP

val dCardinal = listOf(RIGHT, DOWN, LEFT, UP)
val dDiagonal = listOf(RIGHT + DOWN, LEFT + DOWN, LEFT + UP, RIGHT + UP)
val horizontal = listOf(LEFT, RIGHT)
val vertical = listOf(UP, DOWN)
val allDirections = dCardinal + dDiagonal

fun Char.toDirectionOrNull() = when (this) {
    '^', 'U', 'N' -> UP
    'v', 'D', 'S' -> DOWN
    '<', 'L', 'W' -> LEFT
    '>', 'R', 'E' -> RIGHT
    else -> null
}

fun Char.toDirection() = toDirectionOrNull() ?: throw IllegalArgumentException("$this is not a valid direction")

val Direction.isHorizontal get() = this == RIGHT || this == LEFT
val Direction.isVertical get() = this == DOWN || this == UP

fun Direction.turnLeft() = Direction(y, -x)
fun Direction.turnRight() = Direction(-y, x)
fun Direction.orthogonal() = if (isHorizontal) vertical else horizontal

enum class Orientation { HORIZONTAL, VERTICAL }

fun Orientation.orthogonal() = if (this == HORIZONTAL) vertical else horizontal
fun Orientation.turn() = if (this == HORIZONTAL) VERTICAL else HORIZONTAL

data class Point(val x: Int, val y: Int)

fun String.toPoint() = split(",\\s*".toRegex()).map(String::toInt).let { (x, y) -> Point(x, y) }

operator fun Point.plus(other: Point) = Point(x + other.x, y + other.y)
operator fun Point.minus(other: Point) = Point(x - other.x, y - other.y)
operator fun Point.times(other: Point) = Point(x * other.x, y * other.y)
operator fun Point.times(n: Int): Point = Point(x * n, y * n)
operator fun Point.div(n: Int): Point = Point(x / n, y / n)
operator fun Point.unaryMinus() = Point(-x, -y)
operator fun Point.rangeTo(other: Point) = Line(this, other)

val Point.cardinal: List<Point> get() = dCardinal.map { this + it }
val Point.diagonal: List<Point> get() = dDiagonal.map { this + it }
val Point.allNeighbours: List<Point> get() = allDirections.map { this + it }

val Point.manhattan get(): Int = x.absoluteValue + y.absoluteValue
infix fun Point.manhattan(other: Point): Int = (x - other.x).absoluteValue + (y - other.y).absoluteValue
infix fun Point.euclidean(other: Point) = sqrt(euclideanSquared(other).toDouble())
infix fun Point.euclideanSquared(other: Point) = (x - other.x).toLong().let { it * it } + (y - other.y).toLong().let { it * it }

data class Line(val a: Point, val b: Point) {
    val minX get() = minOf(a.x, b.x)
    val minY get() = minOf(a.y, b.y)
    val maxX get() = maxOf(a.x, b.x)
    val maxY get() = maxOf(a.y, b.y)
    val xRange get() = rangeDirection(a.x, b.x)
    val yRange get() = rangeDirection(a.y, b.y)
}

val Line.isHorizontal get() = a.y == b.y
val Line.isVertical get() = a.x == b.x
val Line.isDiagonal get() = (a.x - b.x).absoluteValue == (a.y - b.y).absoluteValue
val Line.isStraight get() = isHorizontal || isVertical
fun Line.allPoints() = when {
    isHorizontal -> xRange.map { Point(it, a.y) }
    isVertical -> yRange.map { Point(a.x, it) }
    isDiagonal -> xRange.zip(yRange) { a, b -> Point(a, b) }
    else -> error("Line must be horizontal, vertical or diagonal (from $a to $b)")
}

fun Line.allPointsSequence() = when {
    isHorizontal -> xRange.asSequence().map { Point(it, a.y) }
    isVertical -> yRange.asSequence().map { Point(a.x, it) }
    isDiagonal -> xRange.asSequence().zip(yRange.asSequence()) { a, b -> Point(a, b) }
    else -> error("Line must be horizontal, vertical or diagonal (from $a to $b)")
}

fun Pair<Point, Point>.toLine() = Line(first, second)

operator fun Line.contains(some: Point) = some.x in minX..maxX && some.y in minY..maxY
fun Line.intersects(other: Line) = intersections(other).isNotEmpty()
fun Line.intersections(other: Line) = allPoints().intersect(other.allPoints().toSet())

fun List<Line>.connect() = flatMap { it.allPoints().dropLast(1) } + last().b

data class Point3D(val x: Int, val y: Int, val z: Int)

fun String.toPoint3D() = split(",\\s*".toRegex()).map(String::toInt).let { (x, y, z) -> Point3D(x, y, z) }

operator fun Point3D.plus(other: Point3D): Point3D = Point3D(x + other.x, y + other.y, z + other.z)

val Point3D.cardinalNeighbours: List<Point3D>
    get() = listOf(
        copy(x = x + 1),
        copy(x = x - 1),
        copy(y = y + 1),
        copy(y = y - 1),
        copy(z = z + 1),
        copy(z = z - 1),
    )

infix fun Point3D.manhattan(other: Point3D): Int = (x - other.x).absoluteValue + (y - other.y).absoluteValue + (z - other.z).absoluteValue
infix fun Point3D.euclidean(other: Point3D) = sqrt(euclideanSquared(other).toDouble())
infix fun Point3D.euclideanSquared(other: Point3D) = (x - other.x).toLong().let { it * it } + (y - other.y).toLong().let { it * it } + (z - other.z).toLong().let { it * it }