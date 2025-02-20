package year2023

import framework.Context
import framework.Day

class Day15(context: Context) : Day by context {
    val sequence = input.split(',')

    fun String.hash() = fold(0) { hash, c -> ((hash + c.code) * 17) and 0xff }

    fun part1() = sequence.sumOf { it.hash() }
    fun part2() = run {
        val boxes = List(256) { mutableMapOf<String, Int>() }

        for (step in sequence) {
            val label = step.takeWhile { it.isLetter() }
            val hash = label.hash()
            when (step.last()) {
                '-' -> boxes[hash] -= label
                else -> boxes[hash][label] = step.last().digitToInt()
            }
        }

        boxes.mapIndexed { i, box ->
            box.values.mapIndexed { j, lens -> (i + 1) * (j + 1) * lens }.sum()
        }.sum()
    }
}