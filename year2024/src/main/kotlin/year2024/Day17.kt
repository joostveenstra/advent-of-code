package year2024

import framework.Day
import util.allInts

object Day17 : Day {
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

    override fun part1(input: String): String {
        val computer = input.allInts().toList().let { Computer(it.drop(3), it.first().toLong()) }
        return generateSequence { computer.run() }.joinToString(",")
    }

    override fun part2(input: String): Long? {
        val instructions = input.allInts().drop(3).toList()

        fun findLowestA(ip: Int, a: Long): Long? =
            if (ip < 0) a
            else (0L..8).firstNotNullOfOrNull { n ->
                val next = (a shl 3) or n
                Computer(instructions, next).run()
                    ?.takeIf { it == instructions[ip].toLong() }
                    ?.let { findLowestA(ip - 1, next) }
            }

        return findLowestA(instructions.lastIndex, 0)
    }
}