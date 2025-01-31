package year2016

import framework.Day
import util.Point
import util.plus
import util.toDirection

object Day2 : Day {
    private fun String.toKeypad() = buildMap {
        lines().forEachIndexed { y, line ->
            line.filterIndexed { i, _ -> i % 2 == 0 }.forEachIndexed { x, char ->
                if (char != ' ') put(Point(x, y), char)
            }
        }
    }

    private fun String.walk(start: Point, keypad: Map<Point, Char>) = lines().scan(start) { button, line ->
        line.fold(button) { position, move ->
            val candidate = position + move.toDirection()
            if (candidate in keypad) candidate else position
        }
    }.drop(1).map { keypad[it] }.joinToString("")

    override fun part1(input: String): String {
        val start = Point(1, 1)
        val keypad = """
            1 2 3
            4 5 6
            7 8 9
        """.trimIndent().toKeypad()
        return input.walk(start, keypad)
    }

    override fun part2(input: String): String {
        val start = Point(0, 2)
        val keypad = """
                1
              2 3 4
            5 6 7 8 9
              A B C
                D
        """.trimIndent().toKeypad()
        return input.walk(start, keypad)
    }
}