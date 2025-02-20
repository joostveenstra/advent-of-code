package year2016

import framework.Context
import framework.Day
import util.transpose

class Day03(context: Context) : Day by context {
    val triples = lines.map { line -> line.trim().split(" +".toRegex()).map { it.toInt() } }

    fun List<Int>.isValidTriangle() = let { (a, b, c) -> a + b > c && b + c > a && c + a > b }

    fun part1() = triples.count { it.isValidTriangle() }
    fun part2() = triples.chunked(3).flatMap { it.transpose() }.count { it.isValidTriangle() }
}