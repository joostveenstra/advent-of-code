package year2017

import framework.Context
import framework.Day
import util.nth
import util.transpose

typealias Pattern = List<List<Char>>

class Day21(context: Context) : Day by context {
    val start = """
        .#.
        ..#
        ###
    """.trimIndent().lines().map { it.toList() }

    fun Pattern.permutations(): List<Pattern> {
        val rotated = generateSequence(this) { it.reversed().transpose() }.take(4).toList()
        return rotated + rotated.map { it.reversed() }
    }

    fun String.toRules() = lines().flatMap { line ->
        val (pattern, enhanced) = line.split(" => ").map { it.split('/').map(String::toList) }
        pattern.permutations().map { it to enhanced }
    }.toMap()

    fun fractal(input: String, iterations: Int): Int {
        val rules = input.toRules()
        fun Pattern.step(): Pattern {
            val size = if (size % 2 == 0) 2 else 3
            return map { it.chunked(size) }
                .chunked(size)
                .flatMap { it.transpose().map { pattern -> rules.getValue(pattern) }.transpose() }
                .map { it.flatten() }
        }
        return generateSequence(start) { it.step() }.nth(iterations).sumOf { row -> row.count { it == '#' } }
    }

    fun part1() = fractal(input, 5)

    fun part2() = fractal(input, 18)
}