package year2022

import framework.Day
import util.Direction
import util.Point
import util.dequeOf
import kotlin.math.sqrt

object Day22 : Day<Int> {
    data class State(val position: Point, val direction: Direction)

    data class Vector(val x: Int, val y: Int, val z: Int) {
        operator fun times(k: Int) = Vector(x * k, y * k, z * k)
        operator fun plus(b: Vector) = Vector(x + b.x, y + b.y, z + b.z)
        infix fun cross(b: Vector) = Vector(y * b.z - z * b.y, z * b.x - x * b.z, x * b.y - y * b.x)
    }

    data class Info(val point: Point, val i: Vector, val j: Vector, val k: Vector)

    data class CubeState(val position: Vector, val direction: Vector)

    private fun String.toTiles() = buildMap {
        lines().dropLast(2).forEachIndexed { y, row ->
            row.forEachIndexed { x, col ->
                if (col != ' ') put(Point(x, y), col == '.')
            }
        }
    }

    private fun String.toMoves() = lines().last().replace("L", " L ").replace("R", " R ").split(" ")
        .flatMap { (if (it.first().isDigit()) "F".repeat(it.toInt()) else it).asIterable() }

    private fun Point.score() = 1000 * (y + 1) + 4 * (x + 1)

    override fun part1(input: String): Int {
        val tiles = input.toTiles()
        val moves = input.toMoves()
        val rows = tiles.keys.groupBy { it.y }
        val cols = tiles.keys.groupBy { it.x }
        val minX = rows.mapValues { (_, row) -> row.minOf { it.x } }
        val maxX = rows.mapValues { (_, row) -> row.maxOf { it.x } }
        val minY = cols.mapValues { (_, col) -> col.minOf { it.y } }
        val maxY = cols.mapValues { (_, col) -> col.maxOf { it.y } }

        val initial = State(Point(minX.getValue(0), 0), Direction.RIGHT)

        val (position, direction) = moves.fold(initial) { (position, direction), move ->
            when (move) {
                'L' -> State(position, direction.turnLeft())
                'R' -> State(position, direction.turnRight())
                else -> {
                    val candidate = position + direction
                    val next = when (tiles[candidate]) {
                        true -> candidate
                        false -> position
                        else -> {
                            val wrapped = when (direction) {
                                Direction.RIGHT -> position.copy(x = minX.getValue(position.y))
                                Direction.LEFT -> position.copy(x = maxX.getValue(position.y))
                                Direction.DOWN -> position.copy(y = minY.getValue(position.x))
                                else -> position.copy(y = maxY.getValue(position.x))
                            }
                            if (tiles.getValue(wrapped)) wrapped else position
                        }
                    }
                    State(next, direction)
                }
            }
        }

        return position.score() + Direction.cardinal.indexOf(direction)
    }

    override fun part2(input: String): Int {
        val tiles = input.toTiles()
        val moves = input.toMoves()
        val blockSize = sqrt((tiles.size / 6).toDouble()).toInt()
        val scaleIJ = blockSize - 1
        val scaleK = blockSize + 1
        val startingPosition = Vector(-scaleIJ, -scaleIJ, -scaleK)
        val startingDirection = Vector(2, 0, 0)
        val initial = CubeState(startingPosition, startingDirection)

        val topLeft = tiles.keys.filter { it.y == 0 }.minBy { it.x }
        val start = Info(topLeft, Vector(1, 0, 0), Vector(0, 1, 0), Vector(0, 0, 1))

        val blocks = dequeOf(start)
        val visited = mutableSetOf(topLeft)
        val points = mutableMapOf<Vector, Info>()

        while (blocks.isNotEmpty()) {
            val (offset, i, j, k) = blocks.removeFirst()

            for (x in 0 until blockSize) for (y in 0 until blockSize) {
                val key = (i * (2 * x - scaleIJ)) + (j * (2 * y - scaleIJ)) + (k * -scaleK)
                points[key] = Info(offset + Point(x, y), i, j, k)
            }

            val neighbours = listOf(
                Info(offset + Point(-blockSize, 0), j cross i, j, j cross k),
                Info(offset + Point(blockSize, 0), i cross j, j, k cross j),
                Info(offset + Point(0, -blockSize), i, j cross i, k cross i),
                Info(offset + Point(0, blockSize), i, i cross j, i cross k)
            )

            neighbours.filter { it.point in tiles }.filterNot { it.point in visited }.forEach { next ->
                blocks.addLast(next)
                visited += next.point
            }
        }

        val (position, direction) = moves.fold(initial) { (position, direction), move ->
            when (move) {
                'L' -> CubeState(position, direction cross points.getValue(position).k)
                'R' -> CubeState(position, points.getValue(position).k cross direction)
                else -> {
                    val candidate = position + direction
                    val isOnCubeFace = candidate in points
                    val nextDirection = if (isOnCubeFace) direction else points.getValue(position).k * 2
                    val nextPosition = if (isOnCubeFace) candidate else candidate + nextDirection
                    if (tiles.getValue(points.getValue(nextPosition).point)) CubeState(nextPosition, nextDirection) else CubeState(position, direction)
                }
            }
        }

        val (point, i, j) = points.getValue(position)
        return point.score() + listOf(i * 2, j * 2, i * -2, j * -2).indexOf(direction)
    }
}