package year2025

import framework.Context
import framework.Day

class Day03(context: Context) : Day by context {
    val banks = lines.map { it.map(Char::digitToInt) }

    fun joltage(digits: Int) = banks.sumOf { bank ->
        (digits downTo 1).fold(0L to 0) { (joltage, start), digit ->
            val end = bank.size - digit
            val i = (start..end).maxBy { bank[it] }
            val max = bank[i]
            10 * joltage + max to i + 1
        }.first
    }

    fun part1() = joltage(2)
    fun part2() = joltage(12)
}