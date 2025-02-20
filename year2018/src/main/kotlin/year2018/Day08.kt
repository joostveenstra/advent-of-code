package year2018

import framework.Context
import framework.Day

class Day08(context: Context) : Day by context {
    data class Node(val children: List<Node>, val metadata: List<Int>)

    val numbers = input.split(' ').map { it.toInt() }
    val node = buildNode(numbers.iterator())

    fun buildNode(numbers: Iterator<Int>): Node {
        val childCount = numbers.next()
        val metadataCount = numbers.next()
        val children = (1..childCount).map { buildNode(numbers) }
        val metadata = (1..metadataCount).map { numbers.next() }
        return Node(children, metadata)
    }

    fun part1(): Int {
        fun Node.sum(): Int = metadata.sum() + children.sumOf { it.sum() }
        return node.sum()
    }

    fun part2(): Int {
        fun Node.sum(): Int = if (children.isEmpty()) metadata.sum() else metadata.mapNotNull { children.getOrNull(it - 1)?.sum() }.sum()
        return node.sum()
    }
}