package year2016

import framework.Context
import framework.Day

class Day25(context: Context) : Day by context {
    sealed interface Instruction
    data class Cpy(val from: String, val to: String) : Instruction
    data class Inc(val to: String) : Instruction
    data class Dec(val to: String) : Instruction
    data class Jnz(val from: String, val offset: Int) : Instruction
    data class Out(val from: String) : Instruction

    sealed interface State
    object Running : State
    object Halted : State
    data class Output(val value: Int) : State

    data class Cpu(val instructions: List<Instruction>, val registers: MutableMap<String, Int>, var i: Int = 0, var state: State = Running) {
        private fun read(key: String) = key.toIntOrNull() ?: registers.getOrDefault(key, 0)
        private fun write(key: String, value: Int) = next().apply { registers[key] = value }
        private fun next() = apply { i += 1; state = Running }
        private fun jump(offset: Int) = apply { i += offset; state = Running }
        private fun output(value: Int) = next().apply { state = Output(value) }
        fun execute() =
            if (i !in instructions.indices) state = Halted
            else with(instructions[i]) {
                when (this) {
                    is Cpy -> write(to, read(from))
                    is Inc -> write(to, read(to) + 1)
                    is Dec -> write(to, read(to) - 1)
                    is Jnz -> if (read(from) != 0) jump(offset) else next()
                    is Out -> output(read(from))
                }
            }
    }

    val program = lines.map { line ->
        line.split(' ').let {
            when (it[0]) {
                "cpy" -> Cpy(it[1], it[2])
                "inc" -> Inc(it[1])
                "dec" -> Dec(it[1])
                "jnz" -> Jnz(it[1], it[2].toInt())
                else -> Out(it[1])
            }
        }
    }

    fun part1() = generateSequence(0) { it + 1 }.map { a ->
        with(Cpu(program, mutableMapOf("a" to a))) {
            val output = mutableListOf<Int>()
            while (output.size < 10) {
                execute()
                if (state is Output) output.add((state as Output).value)
            }
            output.joinToString("")
        }
    }.indexOf("0101010101")

    fun part2() = "Not implemented"
}