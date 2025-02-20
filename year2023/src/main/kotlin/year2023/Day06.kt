package year2023

import framework.Context
import framework.Day
import util.allLongs
import util.productOf

class Day06(context: Context) : Day by context {
    fun race(time: Long, record: Long): Long {
        fun LongProgression.findRecord() = first { hold -> hold * (time - hold) > record }
        val start = (1..time).findRecord()
        val end = (time downTo 1).findRecord()
        return end - start + 1
    }

    fun part1() = run {
        val races = lines.let { (time, record) -> (time.allLongs() zip record.allLongs()).toList() }
        races.productOf { (time, record) -> race(time, record) }
    }

    fun part2() = run {
        val (time, record) = lines.map { line -> line.filter { it.isDigit() }.toLong() }
        race(time, record)
    }
}