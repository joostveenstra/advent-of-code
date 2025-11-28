package year2020

import framework.Context
import framework.Day

class Day02(context: Context) : Day by context {
    data class Rule(val range: IntRange, val letter: Char, val password: String)

    val rules = lines.map { l ->
        val (min, max, letter, password) = l.split('-', ' ')
        Rule(min.toInt()..max.toInt(), letter.first(), password)
    }

    fun part1() = rules.count { (range, letter, password) ->
        password.count { it == letter } in range
    }

    fun part2() = rules.count { (range, letter, password) ->
        (password[range.first - 1] == letter) xor (password[range.last - 1] == letter)
    }
}