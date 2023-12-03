package year2023

import framework.Day

object Day2 : Day<Int> {
    private val max = mapOf("red" to 12, "green" to 13, "blue" to 14)

    override fun part1(input: String) = input.lines()
        .sumOf { line ->
            val (game, remaining) = line.split(": ")
            val id = game.split(" ").last().toInt()
            val sets = remaining.split("; ")
            val isPossible = sets.all { set ->
                val cubes = set.split(", ")
                cubes.all { cube ->
                    val (amount, color) = cube.split(" ")
                    amount.toInt() <= max.getValue(color)
                }
            }
            if (isPossible) id else 0
        }

    override fun part2(input: String) = input.lines()
        .sumOf { line ->
            val (_, remaining) = line.split(": ")
            val sets = remaining.split("; ")
            val count = mutableMapOf<String, Int>()
            sets.forEach { set ->
                val cubes = set.split(", ")
                cubes.forEach { cube ->
                    val (amount, color) = cube.split(" ")
                    count[color] = maxOf(count.getOrDefault(color, 0), amount.toInt())
                }
            }
            count.values.reduce(Int::times)
        }
}