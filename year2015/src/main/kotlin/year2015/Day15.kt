package year2015

import framework.Day
import util.allInts
import util.product
import util.transpose

object Day15 : Day {
    private fun String.toRecipes(): List<List<Int>> {
        val ingredients = lines().map { it.allInts().toList() }
        fun range(offset: Int) = 0..100 - offset
        return buildList {
            for (a in range(0)) for (b in range(a)) for (c in range(a + b)) for (d in range(a + b + c)) {
                if ((a + b + c + d) == 100) {
                    add(listOf(a, b, c, d).zip(ingredients) { tsp, i -> i.map { it * tsp } }.transpose().map { maxOf(0, it.sum()) })
                }
            }
        }
    }

    override fun part1(input: String) = input.toRecipes().maxOf { it.dropLast(1).product() }

    override fun part2(input: String) = input.toRecipes().filter { it.last() == 500 }.maxOf { it.dropLast(1).product() }
}