package year2022

import framework.Context
import framework.Day

class Day21(context: Context) : Day by context {
    sealed interface Monkey {
        companion object {
            fun of(input: String) =
                if (input.first().isDigit())
                    Number(input.toLong())
                else input.split(' ').let { (left, op, right) ->
                    Expression(left, op.first(), right)
                }
        }
    }

    data class Number(val value: Long) : Monkey
    data class Expression(val left: String, val operation: Char, val right: String) : Monkey

    fun evaluateAll(monkeys: Map<String, Monkey>): Map<String, Long> {
        val answers = mutableMapOf<String, Long>()
        fun evaluate(key: String): Long = answers.getOrPut(key) {
            with(monkeys.getValue(key)) {
                when (this) {
                    is Number -> value
                    is Expression -> when (operation) {
                        '+' -> evaluate(left) + evaluate(right)
                        '-' -> evaluate(left) - evaluate(right)
                        '*' -> evaluate(left) * evaluate(right)
                        else -> evaluate(left) / evaluate(right)
                    }
                }
            }
        }
        evaluate("root")
        return answers
    }

    val monkeys = lines.map { it.split(": ") }.associate { (k, v) -> k to Monkey.of(v) }

    fun part1() = evaluateAll(monkeys)["root"]
    fun part2(): Long? {
        val answers = evaluateAll(monkeys)
        fun findHumanValue(key: String, value: Long): Long? = with(monkeys.getValue(key)) {
            when (this) {
                is Number -> if (key == "humn") value else null
                is Expression -> when (key) {
                    "root" -> findHumanValue(right, answers.getValue(left)) ?: findHumanValue(left, answers.getValue(right))
                    else -> when (operation) {
                        '+' -> findHumanValue(right, value - answers.getValue(left))
                        '-' -> findHumanValue(right, answers.getValue(left) - value)
                        '*' -> answers.getValue(left).let { if (it != 0L) findHumanValue(right, value / it) else null }
                        else -> value.let { if (it != 0L) findHumanValue(right, answers.getValue(left) / it) else null }
                    } ?: when (operation) {
                        '+' -> findHumanValue(left, value - answers.getValue(right))
                        '-' -> findHumanValue(left, value + answers.getValue(right))
                        '*' -> answers.getValue(right).let { if (it != 0L) findHumanValue(left, value / it) else null }
                        else -> findHumanValue(left, value * answers.getValue(right))
                    }
                }
            }
        }
        return findHumanValue("root", 0)
    }
}