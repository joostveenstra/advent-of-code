package year2023

import framework.Context
import framework.Day
import util.allInts
import util.size

class Day22(context: Context) : Day by context {
    data class Brick(val xs: IntRange, val ys: IntRange, val zs: IntRange)
    data class Node(val id: Int, val depth: Int)

    fun List<Brick>.fall(): Pair<Int, Int> {
        val heights = Array(10) { IntArray(10) }
        val indices = Array(10) { IntArray(10) { Int.MAX_VALUE } }

        val safe = MutableList(size) { true }
        val dominator = mutableListOf<Node>()

        sortedBy { it.zs.first }.forEachIndexed { i, (xs, ys, zs) ->
            val height = zs.size
            val top = xs.maxOf { x ->
                ys.maxOf { y -> heights[x][y] }
            }
            val underlying = xs.flatMap { x ->
                ys.filter { y -> heights[x][y] == top }.map { y -> indices[x][y] }.filter { it != Int.MAX_VALUE }
            }.toSet()

            for (x in xs) for (y in ys) {
                heights[x][y] = top + height
                indices[x][y] = i
            }

            tailrec fun commonAncestor(a: Node, b: Node): Node = when {
                a.depth > b.depth -> commonAncestor(dominator[a.id], b)
                a.depth < b.depth -> commonAncestor(a, dominator[b.id])
                a.id != b.id -> commonAncestor(dominator[a.id], dominator[b.id])
                else -> a
            }

            val ancestor = when (underlying.size) {
                0 -> Node(0, 0)
                1 -> {
                    val id = underlying.first()
                    safe[id] = false
                    Node(id, dominator[id].depth + 1)
                }

                else -> underlying.map(dominator::get).reduce(::commonAncestor)
            }

            dominator.add(ancestor)
        }

        return safe.count { it } to dominator.sumOf { it.depth }
    }

    val bricks = input.allInts().chunked(6).map { Brick(it[0]..it[3], it[1]..it[4], it[2]..it[5]) }.toList()
    val fallen = bricks.fall()

    fun part1() = fallen.first
    fun part2() = fallen.second
}