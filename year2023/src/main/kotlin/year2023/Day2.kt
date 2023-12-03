package year2023

import framework.Day

object Day2 : Day<Int> {
    private val max = CubeSet(12, 13, 14)

    data class CubeSet(val red: Int, val green: Int, val blue: Int) {
        fun isValid() = red <= max.red && green <= max.green && blue <= max.blue
        fun power() = red * green * blue
    }

    data class Game(val id: Int, val sets: List<CubeSet>) {
        fun isPossible() = sets.all { it.isValid() }
        fun minimalCubeSet() = CubeSet(sets.maxOf { it.red }, sets.maxOf { it.green }, sets.maxOf { it.blue })
    }

    private fun String.toCubeSets(): List<CubeSet> = split("; ").map { set ->
        val cubes = set.split(", ")
            .associate { cube ->
                val (amount, color) = cube.split(" ")
                color to amount.toInt()
            }
            .withDefault { 0 }
        CubeSet(cubes.getValue("red"), cubes.getValue("green"), cubes.getValue("blue"))
    }

    private fun String.toGames() = lines().map { line ->
        val (game, remaining) = line.split(": ")
        val id = game.split(" ").last().toInt()
        val sets = remaining.toCubeSets()
        Game(id, sets)
    }

    override fun part1(input: String) = input.toGames().filter { it.isPossible() }.sumOf { it.id }

    override fun part2(input: String) = input.toGames().sumOf { it.minimalCubeSet().power() }
}