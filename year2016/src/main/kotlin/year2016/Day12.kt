package year2016

import framework.Context
import framework.Day

class Day12(context: Context) : Day by context {
    sealed interface Instruction
    data class Cpy(val from: String, val to: String) : Instruction
    data class Inc(val to: String) : Instruction
    data class Dec(val to: String) : Instruction
    data class Jnz(val from: String, val offset: Int) : Instruction

    data class Cpu(val instructions: List<Instruction>, val registers: MutableMap<String, Int>, var i: Int = 0, var running: Boolean = true) {
        fun read(key: String) = key.toIntOrNull() ?: registers.getOrDefault(key, 0)
        fun write(key: String, value: Int) = next().apply { registers[key] = value }
        fun next() = apply { i += 1 }
        fun jump(offset: Int) = apply { i += offset }
        fun execute() =
            if (i !in instructions.indices) running = false
            else with(instructions[i]) {
                when (this) {
                    is Cpy -> write(to, read(from))
                    is Inc -> write(to, read(to) + 1)
                    is Dec -> write(to, read(to) - 1)
                    is Jnz -> if (read(from) != 0) jump(offset) else next()
                }
            }
    }

    fun List<Instruction>.run(c: Int) = with(Cpu(this, mutableMapOf("c" to c))) {
        while (running) execute()
        registers.getValue("a")
    }

    val program = lines.map { line ->
        line.split(' ').let {
            when (it[0]) {
                "cpy" -> Cpy(it[1], it[2])
                "inc" -> Inc(it[1])
                "dec" -> Dec(it[1])
                else -> Jnz(it[1], it[2].toInt())
            }
        }
    }

    fun part1() = program.run(0)
    fun part2() = program.run(1)
}