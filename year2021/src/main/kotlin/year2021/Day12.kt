package year2021

import framework.Context
import framework.Day
import util.swap
import util.toPair

class Day12(context: Context) : Day by context {
    val caves = lines
        .flatMap { l ->
            val edge = l.split('-').toPair()
            listOf(edge, edge.swap())
        }
        .filterNot { it.second == "start" }
        .groupBy({ it.first }, { it.second })

    val cache = mutableMapOf<Triple<Boolean, String, Set<String>>, Int>()
    fun traverse(
        twice: Boolean,
        node: String = "start",
        visited: Set<String> = setOf(node)
    ): Int = cache.getOrPut(Triple(twice, node, visited)) {
        if (node == "end") return 1
        else caves.getValue(node).sumOf { next ->
            val big = next.first().isUpperCase()
            val once = big || next !in visited
            if (once || twice) {
                val visited = if (big) visited else visited + next
                traverse(twice && once, next, visited)
            } else 0
        }
    }

    fun part1() = traverse(false)
    fun part2() = traverse(true)
}