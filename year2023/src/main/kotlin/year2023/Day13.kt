package year2023

import framework.Day
import util.EMPTY_LINE
import util.transpose

object Day13 : Day {
    private fun String.toPatterns() = split(EMPTY_LINE).map { pattern ->
        val lines = pattern.lines()
        val rows = lines.map { it.toList().toBinary() }
        val columns = lines.map { it.toList() }.transpose().map { it.toBinary() }
        rows to columns
    }

    private fun List<Char>.toBinary() = fold(0) { b, c -> (b shl 1) or if (c == '#') 1 else 0 }

    private fun List<Int>.findReflection(smudges: Int): Int? = (1..<size).find { i ->
        smudges == drop(i).zip(reversed().drop(size - i)).sumOf { (a, b) -> (a xor b).countOneBits() }
    }

    private fun List<Pair<List<Int>, List<Int>>>.summarize(smudges: Int) = sumOf { (rows, columns) ->
        columns.findReflection(smudges)
            ?: rows.findReflection(smudges)?.let { it * 100 }
            ?: error("This should not happen")
    }

    override fun part1(input: String) = input.toPatterns().summarize(0)

    override fun part2(input: String) = input.toPatterns().summarize(1)
}