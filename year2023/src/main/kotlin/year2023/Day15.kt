package year2023

import framework.Day

object Day15 : Day<Int> {
    data class Lens(val label: String, val focalLength: Int)

    private fun String.hash() = fold(0) { hash, c -> ((hash + c.code) * 17) and 0xff }

    override fun part1(input: String) = input.split(',').sumOf { it.hash() }

    override fun part2(input: String): Int {
        val sequence = input.split(',')
        val boxes = List(256) { mutableListOf<Lens>() }

        for (step in sequence) {
            if (step.last() == '-') {
                val label = step.dropLast(1)
                val hash = label.hash()
                val box = boxes[hash]
                val slot = box.indexOfFirst { it.label == label }
                if (slot >= 0) box.removeAt(slot)
            } else {
                val label = step.dropLast(2)
                val focalLength = step.last().digitToInt()
                val lens = Lens(label, focalLength)
                val hash = label.hash()
                val box = boxes[hash]
                val slot = box.indexOfFirst { it.label == label }
                if (slot >= 0) {
                    box[slot] = lens
                } else {
                    box += lens
                }
            }
        }

        return boxes.mapIndexed { i, box -> box.mapIndexed { j, lens -> (i + 1) * (j + 1) * lens.focalLength }.sum() }.sum()
    }
}