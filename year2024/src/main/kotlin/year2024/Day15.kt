package year2024

import framework.Context
import framework.Day
import util.*

class Day15(context: Context) : Day by context {
    val grid = lines.takeWhile { it.isNotEmpty() }.toCharGrid()
    val moves = lines.dropWhile { it.isNotEmpty() }.drop(1).flatMap { l -> l.map { it.toDirection() } }

    fun MutableCharGrid.push(start: Point, direction: Direction): Point {
        val queue = dequeOf(start + direction)
        val path = mutableSetOf<Point>()

        queue.drain { position ->
            if (position !in path) {
                when (get(position)) {
                    '#' -> return start
                    '.' -> return@drain
                    '[' -> queue.add(position + RIGHT)
                    ']' -> queue.add(position + LEFT)
                }
                path.add(position)
                queue.add(position + direction)
            }
        }

        path.reversed().forEach { position ->
            this[position + direction] = this[position]
            this[position] = '.'
        }

        return start + direction
    }

    fun CharGrid.walk(moves: List<Direction>) = toMutableGrid().apply {
        val start = findPoint('@').also { this[it] = '.' }
        moves.fold(start) { position, direction ->
            if (get(position + direction) == '.') position + direction
            else push(position, direction)
        }
    }

    fun CharGrid.spread(): CharGrid {
        val wide = joinToString("") {
            when (it) {
                '#' -> "##"
                'O' -> "[]"
                '@' -> "@."
                else -> ".."
            }
        }.toList()
        return Grid(width * 2, height, wide)
    }

    fun Point.gps() = 100 * y + x

    fun part1() = grid.walk(moves).findPoints('O').sumOf { it.gps() }
    fun part2() = grid.spread().walk(moves).findPoints('[').sumOf { it.gps() }
}