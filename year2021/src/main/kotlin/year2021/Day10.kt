package year2021

import framework.Context
import framework.Day
import util.dequeOf
import util.midpoint
import util.pop
import util.push

class Day10(context: Context) : Day by context {
    val open = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

    sealed interface ParseResult
    class Corrupted(val char: Char) : ParseResult {
        fun score() = when (char) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> error("Unexpected character $char")
        }
    }

    class Incomplete(val closing: List<Char>) : ParseResult {
        fun score() = closing.fold(0L) { acc, c ->
            acc * 5 + when (c) {
                '(' -> 1
                '[' -> 2
                '{' -> 3
                '<' -> 4
                else -> error("Unexpected character $c")
            }
        }
    }

    fun parse(line: String): ParseResult {
        val stack = dequeOf<Char>()
        
        line.forEach { c ->
            when {
                c in open -> stack.push(c)
                c != open[stack.pop()] -> return Corrupted(c)
            }
        }

        return Incomplete(stack)
    }
    
    val result = lines.map(::parse)

    fun part1() = result.filterIsInstance<Corrupted>().sumOf { it.score() }
    fun part2() = result.filterIsInstance<Incomplete>().map { it.score() }.sorted().midpoint()
}