package year2024

import framework.Context
import framework.Day
import util.EMPTY_LINE
import util.drain
import util.toDeque

class Day24(context: Context) : Day by context {
    data class Gate(val left: String, val op: String, val right: String, val out: String)

    val wires = input.split(EMPTY_LINE)[0].lines().associate { it.take(3) to it.last().digitToInt() }
    val gates = input.split(EMPTY_LINE)[1].lines().map { it.split(" ").let { Gate(it[0], it[1], it[2], it[4]) } }

    fun simulate() = wires.toMutableMap().apply {
        val queue = gates.toDeque()
        queue.drain { gate ->
            val (left, op, right, out) = gate
            if (left !in this || right !in this) queue += gate
            else {
                val l = getValue(left)
                val r = getValue(right)
                this[out] = when (op) {
                    "AND" -> l and r
                    "OR" -> l or r
                    "XOR" -> l xor r
                    else -> throw IllegalArgumentException("Invalid op: $op")
                }
            }
        }
    }

    fun part1() = simulate()
        .filterKeys { it.startsWith("z") }
        .entries
        .sortedByDescending { it.key }
        .fold(0L) { output, (_, bit) -> (output shl 1) or bit.toLong() }


    val output = gates.flatMap { (left, op, right) -> listOf(left to op, right to op) }.toSet()

    fun part2() = gates.filter { (left, op, right, out) ->
        when (op) {
            "AND" -> left != "x00" && right != "x00" && (out to "OR") !in output
            "OR" -> out.startsWith('z') && out != "z45" || (out to "OR") in output
            "XOR" -> {
                if (left.startsWith('x') || right.startsWith('x'))
                    left != "x00" && right != "x00" && (out to "XOR") !in output
                else !out.startsWith('z')
            }

            else -> throw IllegalArgumentException("Invalid op: $op")
        }
    }.map { it.out }.sorted().joinToString(",")
}