package year2024

import framework.Context
import framework.Day
import util.allInts

class Day17(context: Context) : Day by context {
    val numbers = input.allInts()
    val a = numbers.first().toLong()
    val instructions = numbers.drop(3).toList()
    val computer = Computer(instructions, a)

    class Computer(
        private val instructions: List<Int>,
        private var a: Long
    ) {
        private var b: Long = 0
        private var c: Long = 0
        private var ip: Int = 0

        private fun Int.toCombo() = when (this) {
            in 0..3 -> toLong()
            4 -> a
            5 -> b
            6 -> c
            else -> throw IllegalArgumentException("Invalid operand $this")
        }

        fun run(): Long? {
            while (ip < instructions.size) {
                val literal = instructions[ip + 1]
                val combo = literal.toCombo()

                when (instructions[ip]) {
                    0 -> a = a shr combo.toInt()
                    1 -> b = b xor literal.toLong()
                    2 -> b = combo % 8
                    3 -> if (a != 0L) {
                        ip = literal
                        continue
                    }

                    4 -> b = b xor c
                    5 -> {
                        ip += 2
                        return combo % 8
                    }

                    6 -> b = a shr combo.toInt()
                    7 -> c = a shr combo.toInt()
                }

                ip += 2
            }

            return null
        }
    }

    fun findLowestA(ip: Int, a: Long): Long? =
        if (ip < 0) a
        else (0L..8).firstNotNullOfOrNull { n ->
            val next = (a shl 3) or n
            Computer(instructions, next).run()
                ?.takeIf { it == instructions[ip].toLong() }
                ?.let { findLowestA(ip - 1, next) }
        }

    fun part1() = generateSequence { computer.run() }.joinToString(",")
    fun part2() = findLowestA(instructions.lastIndex, 0)
}