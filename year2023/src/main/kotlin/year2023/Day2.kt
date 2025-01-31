package year2023

import framework.Day

object Day2 : Day {
    data class Game(val red: Int, val green: Int, val blue: Int) {
        fun isPossible() = red <= 12 && green <= 13 && blue <= 14
        fun power() = red * green * blue
    }

    private fun String.toGames() = lines().map { line ->
        line.split(" ").chunked(2).drop(1).fold(Game(0, 0, 0)) { (r, g, b), (amount, color) ->
            when (color[0]) {
                'r' -> Game(maxOf(r, amount.toInt()), g, b)
                'g' -> Game(r, maxOf(g, amount.toInt()), b)
                else -> Game(r, g, maxOf(b, amount.toInt()))
            }
        }
    }

    override fun part1(input: String) = input.toGames().withIndex().filter { (_, game) -> game.isPossible() }.sumOf { (i) -> i + 1 }

    override fun part2(input: String) = input.toGames().sumOf { it.power() }
}