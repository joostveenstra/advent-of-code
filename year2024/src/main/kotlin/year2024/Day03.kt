package year2024

import framework.Context
import framework.Day
import util.findAll
import util.productOf

class Day03(context: Context) : Day by context {
    val multiply = """mul\((\d+),(\d+)\)""".toRegex()
    val disabled = """don't\(\).*?(?:do\(\)|$)""".toRegex(RegexOption.DOT_MATCHES_ALL)

    fun String.multiply() = findAll(multiply).sumOf { match ->
        match.destructured.toList().productOf { it.toInt() }
    }

    fun part1() = input.multiply()
    fun part2() = input.replace(disabled, "").multiply()
}