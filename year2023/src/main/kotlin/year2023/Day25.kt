package year2023

import framework.Day
import util.dequeOf
import util.drain

object Day25 : Day {
    private fun String.toEdges() = buildMap {
        lines().forEach { line ->
            val (key, remaining) = line.split(": ")
            val neighbours = remaining.split(' ')
            val node = getOrPut(key) { mutableSetOf() }
            neighbours.forEach { n ->
                node += n
                getOrPut(n) { mutableSetOf() } += key
            }
        }
    }

    private fun Map<String, Set<String>>.furthest(start: String): String {
        val queue = dequeOf(start)
        val seen = mutableSetOf(start)

        queue.drain { node ->
            val neighbours = getValue(node).filterNot { it in seen }
            if (neighbours.isEmpty() && queue.isEmpty()) return node
            neighbours.forEach { next ->
                queue += next
                seen += next
            }
        }

        error("This should not happen")
    }

    private fun Map<String, Set<String>>.flow(start: String, end: String): Int {
        val used = mutableSetOf<Pair<String, String>>()

        fun traverse(): Int {
            val path = mutableListOf<Pair<Pair<String, String>, Int>>()
            val queue = dequeOf(start to 0)
            val seen = mutableSetOf(start)

            queue.drain { (node, head) ->
                if (node == end) {
                    tailrec fun reverse(index: Int) {
                        if (index != 0) {
                            val (edge, previous) = path[index]
                            used += edge
                            reverse(previous)
                        }
                    }
                    reverse(head)
                    return 0
                }

                getValue(node).forEach { next ->
                    val edge = node to next
                    if (edge !in used && seen.add(next)) {
                        queue += next to path.size
                        path += edge to head
                    }
                }
            }

            return seen.size
        }

        repeat(3) { traverse() }

        return traverse()
    }

    override fun part1(input: String) = with(input.toEdges()) {
        val start = furthest(keys.first())
        val end = furthest(start)
        val cut = flow(start, end)
        cut * (size - cut)
    }

    override fun part2(input: String) = 0
}