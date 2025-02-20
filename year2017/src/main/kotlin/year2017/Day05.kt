package year2017

import framework.Context
import framework.Day

class Day05(context: Context) : Day by context {
    val instructions = lines.map { it.toInt() }.toList()

    tailrec fun MutableList<Int>.execute(index: Int, steps: Int, jump: (Int) -> Int): Int =
        if (index >= size) steps
        else {
            val offset = this[index]
            this[index] = jump(offset)
            execute(index + offset, steps + 1, jump)
        }

    fun part1() = instructions.toMutableList().execute(0, 0) { offset -> offset + 1 }

    fun part2() = instructions.toMutableList().execute(0, 0) { offset -> if (offset >= 3) offset - 1 else offset + 1 }
}