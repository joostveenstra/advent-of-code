package year2024

import framework.Day
import util.priorityQueueOf
import util.toDeque

object Day9 : Day {
    private fun String.toDisk() = map { it.digitToInt() }

    override fun part1(input: String): Long {
        val blocks = input.toDisk().flatMapIndexed { i, size ->
            List(size) { if (i % 2 == 0) i / 2L else null }
        }
        val free = blocks.indices.filter { blocks[it] == null }.toDeque()

        return blocks.indices.reversed().sumOf { position ->
            val id = blocks[position]
            if (id != null) {
                id * (free.removeFirstOrNull() ?: position)
            } else {
                free.removeLastOrNull()
                0
            }
        }
    }

    override fun part2(input: String): Long {
        val files = mutableListOf<Pair<Int, Int>>()
        val free = List(10) { priorityQueueOf<Int>() }
        input.toDisk().foldIndexed(0) { i, position, size ->
            if (i % 2 == 0) files += position to size else free[size] += position
            position + size
        }

        return files.indices.reversed().sumOf { id ->
            val (position, size) = files[id]
            val available = (size..<free.size).minBy { free[it].peek() ?: position }
            val next = free[available].peek() ?: position
            val start = if (next < position) {
                free[available].poll()
                val remaining = available - size
                if (remaining > 0) free[remaining] += (next + size)
                next
            } else position
            id * (2L * start + size - 1) * size / 2
        }
    }
}