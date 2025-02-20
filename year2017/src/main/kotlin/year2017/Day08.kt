package year2017

import framework.Context
import framework.Day
import util.component6
import util.component7

class Day08(context: Context) : Day by context {
    data class State(val registers: Map<String, Int>, val max: Int)

    val end = lines.map { it.split(' ') }.fold(State(mapOf(), 0)) { state, instruction ->
        val (registers, max) = state
        val (register, operation, amount, _, from, condition, value) = instruction
        val lhs = registers.getOrDefault(from, 0)
        val rhs = value.toInt()
        val execute = when (condition) {
            ">" -> lhs > rhs
            "<" -> lhs < rhs
            ">=" -> lhs >= rhs
            "<=" -> lhs <= rhs
            "==" -> lhs == rhs
            else -> lhs != rhs
        }
        if (execute) {
            val next = registers.getOrDefault(register, 0) + if (operation == "inc") amount.toInt() else -amount.toInt()
            State(registers + (register to next), maxOf(max, next))
        } else {
            state
        }
    }

    fun part1() = end.registers.values.max()
    fun part2() = end.max
}