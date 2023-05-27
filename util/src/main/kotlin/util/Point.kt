package util

import kotlin.math.absoluteValue

typealias Direction = Point

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
    operator fun times(n: Int): Point = Point(x * n, y * n)

    val cardinalNeighbours: List<Point>
        get() = cardinal.map { this + it }

    val diagonalNeighbours: List<Point>
        get() = diagonal.map { this + it }

    val neighbours: List<Point>
        get() = cardinalNeighbours + diagonalNeighbours

    fun manhattan(other: Point): Int = (x - other.x).absoluteValue + (y - other.y).absoluteValue

    fun turnLeft(): Point = Point(y, -x)
    fun turnRight(): Point = Point(-y, x)

    companion object {
        val RIGHT = Point(1, 0)
        val DOWN = Point(0, 1)
        val LEFT = Point(-1, 0)
        val UP = Point(0, -1)
        val EAST = RIGHT
        val SOUTH = DOWN
        val WEST = LEFT
        val NORTH = UP

        val cardinal = listOf(RIGHT, DOWN, LEFT, UP)
        val diagonal = listOf(Point(1, 1), Point(-1, 1), Point(-1, -1), Point(1, -1))

        fun of(input: String): Point = input.split(",\\s*".toRegex()).map(String::toInt).let { (x, y) -> Point(x, y) }
    }
}

data class Point3D(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Point3D): Point3D = Point3D(x + other.x, y + other.y, z + other.z)

    val cardinalNeighbours: List<Point3D>
        get() = listOf(
            copy(x = x + 1),
            copy(x = x - 1),
            copy(y = y + 1),
            copy(y = y - 1),
            copy(z = z + 1),
            copy(z = z - 1),
        )

    fun manhattan(other: Point3D): Int = (x - other.x).absoluteValue + (y - other.y).absoluteValue  + (z - other.z).absoluteValue

    companion object {
        fun of(input: String): Point3D = input.split(",\\s*".toRegex()).map(String::toInt).let { (x, y, z) -> Point3D(x, y, z) }
    }
}