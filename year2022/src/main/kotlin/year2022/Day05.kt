package year2022

import framework.Context
import framework.Day
import util.*

class Day05(context: Context) : Day by context {
    data class Procedure(val amount: Int, val from: Int, val to: Int)

    fun parse(input: String): Pair<List<Deque<Char>>, List<Procedure>> {
        val (stacks, procedures) = input.split(EMPTY_LINE).map { it.lines() }
        return parseStacks(stacks) to parseProcedures(procedures)
    }

    fun parseStacks(input: List<String>): List<Deque<Char>> {
        val crateLines = input.dropLast(1)
        return (1..crateLines.last().length step 4).map { index ->
            crateLines.mapNotNull { it.getOrNull(index) }.filterNot { it == ' ' }.toDeque()
        }
    }

    fun parseProcedures(input: List<String>) = input.map { line ->
        val (amount, from, to) = line.allInts().toList()
        Procedure(amount, from - 1, to - 1)
    }

    fun moveCrates(input: String, reversed: Boolean): String {
        val (stacks, procedures) = parse(input)
        procedures.forEach { (amount, from, to) ->
            val cratesToMove = (1..amount).map { stacks[from].pop() }
            stacks[to].addAll(0, if (reversed) cratesToMove.asReversed() else cratesToMove)
        }
        return stacks.map { it.peek() }.joinToString("")
    }

    fun part1() = moveCrates(input, true)
    fun part2() = moveCrates(input, false)
}