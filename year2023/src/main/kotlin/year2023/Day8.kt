package year2023

import framework.Day
import util.findAll
import util.lcm

object Day8 : Day<Long> {
    data class Node(val left: String, val right: String)

    private fun parse(input: String): Pair<String, Map<String, Node>> = with(input.lines()) {
        val moves = first()
        val nodes = drop(2).associate { line ->
            line.findAll("\\w+".toRegex()).map { it.value }.toList().let { (node, left, right) ->
                node to Node(left, right)
            }
        }
        return moves to nodes
    }

    private fun stepsToEscape(moves: String, nodes: Map<String, Node>, start: String, isEnd: (String) -> Boolean) =
        generateSequence(start to 0) { (current, steps) ->
            val move = moves[steps % moves.length]
            val node = nodes.getValue(current)
            val next = when (move) {
                'L' -> node.left
                else -> node.right
            }
            next to steps + 1
        }.dropWhile { (current) -> !isEnd(current) }.first().let { (_, steps) -> steps }.toLong()


    override fun part1(input: String): Long {
        val (moves, nodes) = parse(input)
        return stepsToEscape(moves, nodes, "AAA") { it == "ZZZ" }
    }

    override fun part2(input: String): Long {
        val (moves, nodes) = parse(input)
        val startNodes = nodes.keys.filter { it.endsWith('A') }
        return startNodes
            .map { start -> stepsToEscape(moves, nodes, start) { it.endsWith('Z') } }
            .reduce(::lcm)
    }
}