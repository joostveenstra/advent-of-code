package year2020

import framework.Context
import framework.Day
import util.combinationSequence
import util.product

class Day01(context: Context) : Day by context {
    val numbers = lines.map { it.toInt() }
    val reverse = numbers.map { 2020 - it }

    fun part1() = numbers.first { it in reverse }.let { it * (2020 - it) }
    fun part2() = numbers.combinationSequence(2).first { 2020 - it.sum() in numbers }.let { (2020 - it.sum()) * it.product() }
}