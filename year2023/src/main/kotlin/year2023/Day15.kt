package year2023

import framework.Day

object Day15 : Day<Int> {
    private fun String.hash() = fold(0) { hash, c -> ((hash + c.code) * 17) and 0xff }

    override fun part1(input: String) = input.split(',').sumOf { it.hash() }

    override fun part2(input: String): Int {
        val sequence = input.split(',')
        val boxes = List(256) { mutableMapOf<String, Int>() }

        for (step in sequence) {
            if (step.last() == '-') {
                val label = step.dropLast(1)
                val box = boxes[label.hash()]
                box.remove(label)
            } else {
                val label = step.dropLast(2)
                val focalLength = step.last().digitToInt()
                val box = boxes[label.hash()]
                box[label] = focalLength
            }
        }

        return boxes.mapIndexed { i, box -> box.values.mapIndexed { j, lens -> (i + 1) * (j + 1) * lens }.sum() }.sum()
    }
}