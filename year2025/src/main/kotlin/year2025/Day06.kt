package year2025

import framework.Context
import framework.Day
import util.allLongs
import util.split
import util.transpose

class Day06(context: Context) : Day by context {
    val operators = lines.last().split("\\s+".toRegex()).map { it.toOperator() }

    fun String.toOperator(): (Long, Long) -> Long = when (this) {
        "+" -> Long::plus
        else -> Long::times
    }

    fun List<List<Long>>.solve() = zip(operators).sumOf { (numbers, op) -> numbers.reduce(op) }
    
    fun part1() = lines.dropLast(1).map { it.allLongs().toList() }
        .transpose()
        .solve()

    fun part2() = lines.dropLast(1).map { it.toList() }
        .transpose()
        .split { col -> col.all { it == ' ' } }
        .map { col -> col.map { it.joinToString("").trim().toLong() } }
        .solve()
}