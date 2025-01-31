package year2017

import framework.Day

object Day9 : Day {
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

    private fun String.process() = fold(State(), State::next)

    override fun part1(input: String) = input.process().score

    override fun part2(input: String) = input.process().characters
}