package year2023

import framework.Day
import util.allLongs
import util.productOf

object Day6 : Day<Int> {
    private fun wins(time: Long, record: Long) = (0L..time).asSequence()
        .map { hold -> hold * (time - hold) }
        .count { it > record }

    override fun part1(input: String): Int {
        val races = input.lines().let { (time, record) -> (time.allLongs() zip record.allLongs()).toList() }
        return races.productOf { (time, record) -> wins(time, record) }
    }

    override fun part2(input: String): Int {
        val (time, record) = input.lines().map { line -> line.filter { it.isDigit() }.toLong() }
        return wins(time, record)
    }
}