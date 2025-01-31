package year2017

import framework.Day
import util.*

object Day19 : Day {
    private fun String.walk() = with(toCharGrid()) {
        tailrec fun next(position: Point, direction: Direction, path: List<Char>, steps: Int): Pair<String, Int> {
            val nextPosition = position + direction
            return when (val value = get(nextPosition)) {
                ' ' -> path.joinToString("") to steps
                '+' -> {
                    val (first, second) = if (direction.isHorizontal) vertical else horizontal
                    val nextDirection = if (get(nextPosition + first) != ' ') first else second
                    next(nextPosition, nextDirection, path, steps + 1)
                }

                else -> when {
                    value.isLetter() -> next(nextPosition, direction, path + value, steps + 1)
                    else -> next(nextPosition, direction, path, steps + 1)
                }
            }
        }

        val start = Point(indexOf('|'), -1)
        next(start, DOWN, listOf(), 0)
    }

    override fun part1(input: String) = input.walk().first

    override fun part2(input: String) = input.walk().second
}