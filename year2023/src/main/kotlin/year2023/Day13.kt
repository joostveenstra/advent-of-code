package year2023

import framework.Context
import framework.Day
import util.EMPTY_LINE
import util.transpose

class Day13(context: Context) : Day by context {
    val patterns = this@Day13.input.split(EMPTY_LINE).map { pattern ->
        val lines = pattern.lines()
        val rows = lines.map { it.toList().toBinary() }
        val columns = lines.map { it.toList() }.transpose().map { it.toBinary() }
        rows to columns
    }

    fun List<Char>.toBinary() = fold(0) { b, c -> (b shl 1) or if (c == '#') 1 else 0 }

    fun List<Int>.findReflection(smudges: Int): Int? = (1..<size).find { i ->
        smudges == drop(i).zip(reversed().drop(size - i)).sumOf { (a, b) -> (a xor b).countOneBits() }
    }

    fun List<Pair<List<Int>, List<Int>>>.summarize(smudges: Int) = sumOf { (rows, columns) ->
        columns.findReflection(smudges)
            ?: rows.findReflection(smudges)?.let { it * 100 }
            ?: error("This should not happen")
    }

    fun part1() = patterns.summarize(0)
    fun part2() = patterns.summarize(1)
}