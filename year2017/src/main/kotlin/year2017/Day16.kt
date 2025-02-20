package year2017

import framework.Context
import framework.Day
import util.match

class Day16(context: Context) : Day by context {
    val spin = "s(\\d+)".toRegex()
    val exchange = "x(\\d+)/(\\d+)".toRegex()
    val partner = "p(\\w)/(\\w)".toRegex()

    val programs = if (isExample) "abcde" else "abcdefghijklmnop"
    val moves = input.split(',')

    fun List<String>.dance(initial: String) = fold(initial) { programs, move ->
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

    fun part1() = moves.dance(programs)
    fun part2(): String {
        val dances = generateSequence(programs) { moves.dance(it) }
        val period = dances.drop(1).indexOf(programs) + 1
        return dances.drop(1000000000 % period).first()
    }
}