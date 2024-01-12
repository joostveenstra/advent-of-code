package year2023

import framework.Day
import util.allLongs
import util.productOf

object Day6 : Day<Long> {
    private fun race(time: Long, record: Long): Long {
        fun LongProgression.findRecord() = first { hold -> hold * (time - hold) > record }
        val start = (1..time).findRecord()
        val end = (time downTo 1).findRecord()
        return end - start + 1
    }

    override fun part1(input: String): Long {
        val races = input.lines().let { (time, record) -> (time.allLongs() zip record.allLongs()).toList() }
        return races.productOf { (time, record) -> race(time, record) }
    }

    override fun part2(input: String): Long {
        val (time, record) = input.lines().map { line -> line.filter { it.isDigit() }.toLong() }
        return race(time, record)
    }
}