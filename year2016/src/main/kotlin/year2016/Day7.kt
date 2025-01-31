package year2016

import framework.Day

object Day7 : Day {
    private fun parse(input: String) = input.lines().map { line ->
        val (accept, reject) = line.split("[\\[\\]]".toRegex()).withIndex().partition { (index) -> index % 2 == 0 }
        accept.map { it.value } to reject.map { it.value }
    }

    private fun String.containsABBA() = asIterable().windowed(4).any { (a, b, c, d) -> a == d && b == c && a != b }

    private fun String.toABAs() = asIterable().windowed(3).mapNotNull { (a, b, c) -> if (a == c && a != b) "$a$b$a" else null }

    private fun String.toBABs() = asIterable().windowed(3).mapNotNull { (a, b, c) -> if (a == c && a != b) "$b$a$b" else null }

    override fun part1(input: String) = parse(input).count { (accept, reject) ->
        accept.any { it.containsABBA() } && reject.none { it.containsABBA() }
    }

    override fun part2(input: String) = parse(input).count { (accept, reject) ->
        (accept.flatMap { it.toABAs() } intersect reject.flatMap { it.toBABs() }.toSet()).isNotEmpty()
    }
}