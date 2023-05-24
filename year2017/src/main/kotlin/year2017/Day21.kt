package year2017

import framework.Day
import util.transpose

typealias Pattern = List<List<Char>>

object Day21 : Day<Int> {
    private val start = """
        .#.
        ..#
        ###
    """.trimIndent().lines().map { it.toList() }

    private fun Pattern.permutations(): List<Pattern> {
        val rotated = generateSequence(this) { it.reversed().transpose() }.take(4).toList()
        return rotated + rotated.map { it.reversed() }
    }

    private fun String.toRules() = lines().flatMap { line ->
        val (pattern, enhanced) = line.split(" => ").map { it.split('/').map(String::toList) }
        pattern.permutations().map { it to enhanced }
    }.toMap()

    private fun fractal(input: String, iterations: Int): Int {
        val rules = input.toRules()
        fun Pattern.step(): Pattern {
            val size = if (size % 2 == 0) 2 else 3
            return map { it.chunked(size) }
                .chunked(size)
                .flatMap { it.transpose().map { pattern -> rules.getValue(pattern) }.transpose() }
                .map { it.flatten() }
        }
        return generateSequence(start) { it.step() }.drop(iterations).first().sumOf { row -> row.count { it == '#' } }
    }

    override fun part1(input: String) = fractal(input, 5)

    override fun part2(input: String) = fractal(input, 18)
}