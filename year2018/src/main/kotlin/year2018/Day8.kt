package year2018

import framework.Day

object Day8 : Day {
    data class Node(val children: List<Node>, val metadata: List<Int>)

    private fun String.toNumbers() = split(' ').map { it.toInt() }.iterator()

    private fun parse(numbers: Iterator<Int>): Node {
        val childCount = numbers.next()
        val metadataCount = numbers.next()
        val children = (1..childCount).map { parse(numbers) }
        val metadata = (1..metadataCount).map { numbers.next() }
        return Node(children, metadata)
    }

    override fun part1(input: String): Int {
        fun Node.sum(): Int = metadata.sum() + children.sumOf { it.sum() }
        return parse(input.toNumbers()).sum()
    }

    override fun part2(input: String): Int {
        fun Node.sum(): Int = if (children.isEmpty()) metadata.sum() else metadata.mapNotNull { children.getOrNull(it - 1)?.sum() }.sum()
        return parse(input.toNumbers()).sum()
    }
}