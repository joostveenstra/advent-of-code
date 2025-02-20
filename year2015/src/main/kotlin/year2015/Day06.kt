package year2015

import framework.Context
import framework.Day
import util.allInts

class Day06(context: Context) : Day by context {
    fun List<String>.execute(algorithm: (String) -> (Int) -> Int): Int {
        val grid = Array(1000) { IntArray(1000) }

        forEach { line ->
            val instruction = algorithm(line)
            val (x1, y1, x2, y2) = line.allInts().toList()
            for (x in x1..x2) for (y in y1..y2) grid[y][x] = instruction(grid[y][x])
        }

        return grid.sumOf { it.sum() }
    }

    fun part1() = lines.execute { line ->
        when {
            line.startsWith("turn on") -> { _ -> 1 }
            line.startsWith("turn off") -> { _ -> 0 }
            else -> { p -> if (p == 1) 0 else 1 }
        }
    }

    fun part2() = lines.execute { line ->
        when {
            line.startsWith("turn on") -> { p -> p + 1 }
            line.startsWith("turn off") -> { p -> maxOf(0, p - 1) }
            else -> { p -> p + 2 }
        }
    }
}