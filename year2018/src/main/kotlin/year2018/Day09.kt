package year2018

import framework.Context
import framework.Day
import util.allInts

class Day09(context: Context) : Day by context {
    data class Node(val value: Int, var previous: Node?, var next: Node?)

    fun String.play(multiplier: Int): Long {
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

    fun part1() = input.play(1)
    fun part2() = input.play(100)
}