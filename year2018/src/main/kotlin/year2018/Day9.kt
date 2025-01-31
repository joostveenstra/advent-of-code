package year2018

import framework.Day
import util.allInts

object Day9 : Day {
    data class Node(val value: Int, var previous: Node?, var next: Node?)

    private fun String.play(multiplier: Int): Long {
        val (players, lastMarble) = allInts().toList()
        val last = lastMarble * multiplier
        val scores = LongArray(players) { 0 }
        val start = Node(0, null, null).apply {
            previous = this
            next = this
        }

        tailrec fun highScore(current: Node, marble: Int): Long =
            if (marble > last) scores.max()
            else {
                val next =
                    if (marble % 23 == 0) {
                        val node = (1..7).fold(current) { node, _ -> node.previous!! }
                        node.previous!!.next = node.next!!
                        node.next!!.previous = node.previous!!
                        scores[marble % players] += (marble + node.value).toLong()
                        node.next!!
                    } else {
                        val left = current.next!!
                        val right = current.next!!.next!!
                        val node = Node(marble, left, right)
                        left.next = node
                        right.previous = node
                        node
                    }
                highScore(next, marble + 1)
            }

        return highScore(start, 1)
    }

    override fun part1(input: String) = input.play(1)

    override fun part2(input: String) = input.play(100)
}