package year2024

import framework.Day
import util.*

object Day15 : Day {
    private fun CharGrid.walk(moves: List<Direction>) = toMutableGrid().apply {
        val start = findPoint('@').also { this[it] = '.' }
        moves.fold(start) { position, direction ->
            if (get(position + direction) == '.') position + direction
            else push(position, direction)
        }
    }

    private fun MutableCharGrid.push(start: Point, direction: Direction): Point {
        val queue = dequeOf(start + direction)
        val path = mutableSetOf<Point>()

        queue.drain { position ->
            if (position !in path) {
                when (get(position)) {
                    '#' -> return start
                    '.' -> return@drain
                    '[' -> queue += position + RIGHT
                    ']' -> queue += position + LEFT
                }
                path += position
                queue += position + direction
            }
        }

        path.reversed().forEach { position ->
            this[position + direction] = this[position]
            this[position] = '.'
        }

        return start + direction
    }

    private fun CharGrid.spread(): CharGrid {
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

    private fun Point.gps() = 100 * y + x

    private fun parse(input: String) = input.split(EMPTY_LINE).let { (g, m) ->
        val grid = g.toCharGrid()
        val moves = m.lines().flatMap { l -> l.map { it.toDirection() } }
        grid to moves
    }

    override fun part1(input: String) = parse(input).let { (grid, moves) ->
        grid.walk(moves).findPoints('O').sumOf { it.gps() }
    }

    override fun part2(input: String) = parse(input).let { (grid, moves) ->
        grid.spread().walk(moves).findPoints('[').sumOf { it.gps() }
    }
}