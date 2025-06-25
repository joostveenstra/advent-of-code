package year2017

import framework.Context
import framework.Day
import util.*

class Day19(context: Context) : Day by context {
    fun String.walk() = with(toCharGrid()) {
        tailrec fun next(position: Point, direction: Direction, path: List<Char>, steps: Int): Pair<String, Int> {
            val nextPosition = position + direction
            return when (val value = get(nextPosition)) {
                ' ' -> path.joinToString("") to steps
                '+' -> {
                    val (first, second) = if (direction.isHorizontal) vertical else horizontal
                    val nextDirection = if (get(nextPosition + first) != ' ') first else second
                    next(nextPosition, nextDirection, path, steps + 1)
                }

                else if (value.isLetter()) -> next(nextPosition, direction, path + value, steps + 1)
                else -> next(nextPosition, direction, path, steps + 1)
            }
        }

        val start = Point(indexOf('|'), -1)
        next(start, DOWN, listOf(), 0)
    }

    fun part1() = input.walk().first
    fun part2() = input.walk().second
}