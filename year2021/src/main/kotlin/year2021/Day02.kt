package year2021

import framework.Context
import framework.Day

class Day02(context: Context) : Day by context {
    data class Sub(val position: Int, val depth: Int, val aim: Int = 0)

    val moves = lines.map { it.split(" ").let { (command, amount) -> command to amount.toInt() } }

    fun part1() = moves.fold(Sub(0, 0)) { (position, depth), (command, amount) ->
        when (command) {
            "up" -> Sub(position, depth - amount)
            "down" -> Sub(position, depth + amount)
            else -> Sub(position + amount, depth)
        }
    }.let { it.position * it.depth }

    fun part2() = moves.fold(Sub(0, 0, 0)) { (position, depth, aim), (command, amount) ->
        when (command) {
            "up" -> Sub(position, depth, aim - amount)
            "down" -> Sub(position, depth, aim + amount)
            else -> Sub(position + amount, depth + aim * amount, aim)
        }
    }.let { it.position * it.depth }
}