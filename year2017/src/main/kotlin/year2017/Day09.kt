package year2017

import framework.Context
import framework.Day

class Day09(context: Context) : Day by context {
    data class State(val depth: Int = 1, val score: Int = 0, val ignore: Boolean = false, val garbage: Boolean = false, val characters: Int = 0) {
        fun next(c: Char) = when {
            ignore -> copy(ignore = false)
            c == '!' -> copy(ignore = true)
            c == '>' -> copy(garbage = false)
            garbage -> copy(characters = characters + 1)
            c == '<' -> copy(garbage = true)
            c == '{' -> copy(depth = depth + 1, score = score + depth)
            c == '}' -> copy(depth = depth - 1)
            else -> this
        }
    }

    val end = input.fold(State(), State::next)

    fun part1() = end.score
    fun part2() = end.characters
}