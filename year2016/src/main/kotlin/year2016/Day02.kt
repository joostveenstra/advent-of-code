package year2016

import framework.Context
import framework.Day
import util.Point
import util.plus
import util.toDirection

class Day02(context: Context) : Day by context {
    fun String.toKeypad() = buildMap {
        lines().forEachIndexed { y, line ->
            line.filterIndexed { i, _ -> i % 2 == 0 }.forEachIndexed { x, char ->
                if (char != ' ') put(Point(x, y), char)
            }
        }
    }

    fun String.walk(start: Point, keypad: Map<Point, Char>) = lines().scan(start) { button, line ->
        line.fold(button) { position, move ->
            val candidate = position + move.toDirection()
            if (candidate in keypad) candidate else position
        }
    }.drop(1).map { keypad[it] }.joinToString("")

    fun part1() = run {
        val start = Point(1, 1)
        val keypad = """
            1 2 3
            4 5 6
            7 8 9
        """.trimIndent().toKeypad()
        this.input.walk(start, keypad)
    }

    fun part2() = run {
        val start = Point(0, 2)
        val keypad = """
                1
              2 3 4
            5 6 7 8 9
              A B C
                D
        """.trimIndent().toKeypad()
        this.input.walk(start, keypad)
    }
}