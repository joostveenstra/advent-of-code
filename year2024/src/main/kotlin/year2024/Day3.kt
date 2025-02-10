package year2024

import framework.Day
import util.findAll
import util.productOf

object Day3 : Day {
    private val multiply = """mul\((\d+),(\d+)\)""".toRegex()
    private val disabled = """don't\(\).*?(?:do\(\)|$)""".toRegex(RegexOption.DOT_MATCHES_ALL)

    private fun String.multiply() = findAll(multiply).sumOf { match ->
        match.destructured.toList().productOf { it.toInt() }
    }

    override fun part1(input: String) = input.multiply()

    override fun part2(input: String) = input.replace(disabled, "").multiply()
}