package year2015

import framework.Context
import framework.Day

class Day08(context: Context) : Day by context {
    fun part1() = lines.sumOf { line ->
        line.length - line.drop(1).dropLast(1)
            .replace("\\\\", "a")
            .replace("\\\"", "b")
            .replace("\\\\x..".toRegex(), "c")
            .length
    }

    fun part2() = lines.sumOf { line ->
        2 + line.count { it == '\\' || it == '\"' }
    }
}