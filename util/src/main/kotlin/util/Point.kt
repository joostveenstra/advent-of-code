package util

import util.Orientation.HORIZONTAL
import util.Orientation.VERTICAL
import kotlin.math.absoluteValue

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

val cardinal = listOf(RIGHT, DOWN, LEFT, UP)
val diagonal = listOf(RIGHT + DOWN, LEFT + DOWN, LEFT + UP, RIGHT + UP)
val horizontal = listOf(LEFT, RIGHT)
val vertical = listOf(UP, DOWN)
val allDirections = cardinal + diagonal

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

val Point.cardinalNeighbours: List<Point> get() = cardinal.map { this + it }
val Point.diagonalNeighbours: List<Point> get() = diagonal.map { this + it }
val Point.allNeighbours: List<Point> get() = allDirections.map { this + it }

val Point.manhattan get(): Int = x.absoluteValue + y.absoluteValue
infix fun Point.manhattan(other: Point): Int = (x - other.x).absoluteValue + (y - other.y).absoluteValue

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

fun Point3D.manhattan(other: Point3D): Int = (x - other.x).absoluteValue + (y - other.y).absoluteValue + (z - other.z).absoluteValue