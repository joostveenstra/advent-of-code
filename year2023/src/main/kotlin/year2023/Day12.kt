package year2023

import framework.Day

object Day12 : Day {
    private fun String.toRows(multiplier: Int) = lines().map { line ->
        line.split(' ').let { (left, right) ->
            val springs = List(multiplier) { left }.joinToString("?")
            val sizes = List(multiplier) { right }.joinToString(",")
            springs to sizes.split(',').map { it.toInt() }
        }
    }

    private fun Pair<String, List<Int>>.arrangements() = let { (springs, sizes) ->
        val cache = mutableMapOf<Triple<String, List<Int>, Int>, Long>()
        fun arrangements(springs: String, sizes: List<Int>, done: Int = 0): Long = cache.getOrPut(Triple(springs, sizes, done)) {
            when {
                springs.isEmpty() -> if (sizes.isEmpty() && done == 0) 1 else 0
                else -> {
                    val candidates = springs.first().let { if (it == '?') listOf('.', '#') else listOf(it) }
                    candidates.sumOf { spring ->
                        when (spring) {
                            '#' -> arrangements(springs.drop(1), sizes, done + 1)
                            else -> when (done) {
                                0 -> arrangements(springs.drop(1), sizes)
                                sizes.firstOrNull() -> arrangements(springs.drop(1), sizes.drop(1))
                                else -> 0
                            }
                        }
                    }
                }
            }
        }
        arrangements("$springs.", sizes)
    }

    override fun part1(input: String) = input.toRows(1).sumOf { it.arrangements() }

    override fun part2(input: String) = input.toRows(5).sumOf { it.arrangements() }
}