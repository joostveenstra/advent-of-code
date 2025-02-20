package year2024

import framework.Context
import framework.Day
import util.priorityQueueOf
import util.toDeque

class Day09(context: Context) : Day by context {
    val disk = input.map { it.digitToInt() }

    fun part1() = run {
        val blocks = disk.flatMapIndexed { i, size ->
            List(size) { if (i % 2 == 0) i / 2L else null }
        }
        val empty = blocks.indices.filter { blocks[it] == null }.toDeque()

        blocks.indices.reversed().sumOf { position ->
            val id = blocks[position]
            if (id != null) {
                id * (empty.removeFirstOrNull() ?: position)
            } else {
                empty.removeLastOrNull()
                0
            }
        }
    }

    fun part2() = run {
        val files = mutableListOf<Pair<Int, Int>>()
        val free = List(10) { priorityQueueOf<Int>() }
        disk.foldIndexed(0) { i, position, size ->
            if (i % 2 == 0) files += position to size else free[size] += position
            position + size
        }

        files.indices.reversed().sumOf { id ->
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