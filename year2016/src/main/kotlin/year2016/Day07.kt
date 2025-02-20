package year2016

import framework.Context
import framework.Day

class Day07(context: Context) : Day by context {
    val ips = lines.map { line ->
        val (accept, reject) = line.split("[\\[\\]]".toRegex()).withIndex().partition { (index) -> index % 2 == 0 }
        accept.map { it.value } to reject.map { it.value }
    }

    fun String.containsABBA() = asIterable().windowed(4).any { (a, b, c, d) -> a == d && b == c && a != b }
    fun String.toABAs() = asIterable().windowed(3).mapNotNull { (a, b, c) -> if (a == c && a != b) "$a$b$a" else null }
    fun String.toBABs() = asIterable().windowed(3).mapNotNull { (a, b, c) -> if (a == c && a != b) "$b$a$b" else null }

    fun part1() = ips.count { (accept, reject) ->
        accept.any { it.containsABBA() } && reject.none { it.containsABBA() }
    }

    fun part2() = ips.count { (accept, reject) ->
        (accept.flatMap { it.toABAs() } intersect reject.flatMap { it.toBABs() }.toSet()).isNotEmpty()
    }
}