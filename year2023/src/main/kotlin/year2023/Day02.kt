package year2023

import framework.Context
import framework.Day

class Day02(context: Context) : Day by context {
    data class Game(val red: Int, val green: Int, val blue: Int) {
        fun isPossible() = red <= 12 && green <= 13 && blue <= 14
        fun power() = red * green * blue
    }

    val games = lines.map { line ->
        line.split(" ").chunked(2).drop(1).fold(Game(0, 0, 0)) { (r, g, b), (amount, color) ->
            when (color[0]) {
                'r' -> Game(maxOf(r, amount.toInt()), g, b)
                'g' -> Game(r, maxOf(g, amount.toInt()), b)
                else -> Game(r, g, maxOf(b, amount.toInt()))
            }
        }
    }

    fun part1() = games.withIndex().filter { (_, game) -> game.isPossible() }.sumOf { (i) -> i + 1 }
    fun part2() = games.sumOf { it.power() }
}