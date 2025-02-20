package year2016

import framework.Context
import framework.Day
import util.Point
import util.allInts

class Day08(context: Context) : Day by context {
    sealed interface Operation
    data class Rect(val width: Int, val height: Int) : Operation
    data class Row(val row: Int, val offset: Int) : Operation
    data class Col(val col: Int, val offset: Int) : Operation

    data class Screen(val width: Int, val height: Int)

    val screen = if (isExample) Screen(7, 3) else Screen(50, 6)
    val operations = lines.map { line ->
        val (a, b) = line.allInts().toList()
        when {
            "rect" in line -> Rect(a, b)
            "row" in line -> Row(a, b)
            else -> Col(a, b)
        }
    }

    fun Screen.draw(operations: List<Operation>): Set<Point> = operations.fold(setOf()) { points, op ->
        when (op) {
            is Rect -> points + (0 until op.width).flatMap { x -> (0 until op.height).map { y -> Point(x, y) } }
            is Row -> points.map { p -> if (op.row != p.y) p else Point((p.x + op.offset) % width, p.y) }.toSet()
            is Col -> points.map { p -> if (op.col != p.x) p else Point(p.x, (p.y + op.offset) % height) }.toSet()
        }
    }

    val points = screen.draw(operations)

    fun part1() = points.size
    fun part2() = (0 until screen.height).joinToString("\n") { y ->
        (0 until screen.width).joinToString("") { x ->
            if (Point(x, y) in points) "#" else "."
        }
    }
}