package year2023

import framework.Context
import framework.Day
import util.findAll
import util.lcm

class Day08(context: Context) : Day by context {
    data class Node(val left: String, val right: String)

    val moves = lines.first()
    val nodes = lines.drop(2).associate { line ->
        line.findAll("\\w+".toRegex()).map { it.value }.toList().let { (key, left, right) ->
            key to Node(left, right)
        }
    }

    fun stepsToEscape(moves: String, nodes: Map<String, Node>, start: String) =
        generateSequence(start to 0) { (current, steps) ->
            val move = moves[steps % moves.length]
            val node = nodes.getValue(current)
            val next = when (move) {
                'L' -> node.left
                else -> node.right
            }
            next to steps + 1
        }.dropWhile { (current) -> !current.endsWith('Z') }.first().let { (_, steps) -> steps }.toLong()

    fun part1() = stepsToEscape(moves, nodes, "AAA")
    fun part2() = nodes.keys
        .filter { it.endsWith('A') }
        .map { start -> stepsToEscape(moves, nodes, start) }
        .reduce(Long::lcm)
}