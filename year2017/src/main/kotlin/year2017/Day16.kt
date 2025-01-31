package year2017

import framework.Day
import util.match

object Day16 : Day {
    private val spin = "s(\\d+)".toRegex()
    private val exchange = "x(\\d+)/(\\d+)".toRegex()
    private val partner = "p(\\w)/(\\w)".toRegex()

    private fun String.toPrograms() = if (length < 14) "abcde" else "abcdefghijklmnop"

    private fun List<String>.dance(initial: String) = fold(initial) { programs, move ->
        move.match(spin)?.let { (a) ->
            val x = a.toInt()
            programs.takeLast(x) + programs.dropLast(x)
        } ?: move.match(exchange)?.let { (a, b) ->
            val x = a.toInt()
            val y = b.toInt()
            programs.mapIndexed { index, p ->
                when (index) {
                    x -> programs[y]
                    y -> programs[x]
                    else -> p
                }
            }.joinToString("")
        } ?: move.match(partner)?.let { (a, b) ->
            val x = programs.indexOf(a)
            val y = programs.indexOf(b)
            programs.mapIndexed { index, p ->
                when (index) {
                    x -> programs[y]
                    y -> programs[x]
                    else -> p
                }
            }.joinToString("")
        } ?: throw IllegalArgumentException("$move is not a valid move")
    }

    override fun part1(input: String) = input.split(',').dance(input.toPrograms())

    override fun part2(input: String): String {
        val initial = input.toPrograms()
        val moves = input.split(',')
        val dances = generateSequence(initial) { moves.dance(it) }
        val period = dances.drop(1).indexOf(initial) + 1
        return dances.drop(1000000000 % period).first()
    }
}