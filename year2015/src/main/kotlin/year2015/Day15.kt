package year2015

import framework.Context
import framework.Day
import util.allIntsSigned
import util.product
import util.transpose

class Day15(context: Context) : Day by context {
    val recipes = lines.run {
        val ingredients = map { it.allIntsSigned().toList() }
        fun range(offset: Int) = 0..100 - offset
        buildList {
            for (a in range(0)) for (b in range(a)) for (c in range(a + b)) for (d in range(a + b + c)) {
                if ((a + b + c + d) == 100) {
                    add(listOf(a, b, c, d).zip(ingredients) { tsp, i -> i.map { it * tsp } }.transpose().map { maxOf(0, it.sum()) })
                }
            }
        }
    }

    fun part1() = recipes.maxOf { it.dropLast(1).product() }
    fun part2() = recipes.filter { it.last() == 500 }.maxOf { it.dropLast(1).product() }
}