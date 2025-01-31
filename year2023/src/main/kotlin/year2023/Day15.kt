package year2023

import framework.Day

object Day15 : Day {
    private fun String.hash() = fold(0) { hash, c -> ((hash + c.code) * 17) and 0xff }

    override fun part1(input: String) = input.split(',').sumOf { it.hash() }

    override fun part2(input: String): Int {
        val sequence = input.split(',')
        val boxes = List(256) { mutableMapOf<String, Int>() }

        for (step in sequence) {
            val label = step.takeWhile { it.isLetter() }
            val hash = label.hash()
            when (step.last()) {
                '-' -> boxes[hash] -= label
                else -> boxes[hash][label] = step.last().digitToInt()
            }
        }

        return boxes.mapIndexed { i, box -> box.values.mapIndexed { j, lens -> (i + 1) * (j + 1) * lens }.sum() }.sum()
    }
}