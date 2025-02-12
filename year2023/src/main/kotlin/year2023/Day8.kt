package year2023

import framework.Day
import util.findAll
import util.lcm

object Day8 : Day {
    data class Node(val left: String, val right: String)

    private fun parse(input: String): Pair<String, Map<String, Node>> = with(input.lines()) {
        val moves = first()
        val nodes = drop(2).associate { line ->
            line.findAll("\\w+".toRegex()).map { it.value }.toList().let { (key, left, right) ->
                key to Node(left, right)
            }
        }
        moves to nodes
    }

    private fun stepsToEscape(moves: String, nodes: Map<String, Node>, start: String) =
        generateSequence(start to 0) { (current, steps) ->
            val move = moves[steps % moves.length]
            val node = nodes.getValue(current)
            val next = when (move) {
                'L' -> node.left
                else -> node.right
            }
            next to steps + 1
        }.dropWhile { (current) -> !current.endsWith('Z') }.first().let { (_, steps) -> steps }.toLong()


    override fun part1(input: String): Long {
        val (moves, nodes) = parse(input)
        return stepsToEscape(moves, nodes, "AAA")
    }

    override fun part2(input: String): Long {
        val (moves, nodes) = parse(input)
        val startNodes = nodes.keys.filter { it.endsWith('A') }
        return startNodes
            .map { start -> stepsToEscape(moves, nodes, start) }
            .reduce(Long::lcm)
    }
}