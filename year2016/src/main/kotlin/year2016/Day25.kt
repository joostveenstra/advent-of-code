package year2016

import framework.Day
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.plus

object Day25 : Day {
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

    data class Cpu(val instructions: List<Instruction>, val registers: PersistentMap<String, Int>, val i: Int = 0, val state: State = Running) {
        private fun read(key: String) = key.toIntOrNull() ?: registers.getOrDefault(key, 0)
        private fun write(key: String, value: Int) = next().copy(registers = registers + (key to value))
        private fun next() = copy(i = i + 1, state = Running)
        private fun jump(offset: Int) = copy(i = i + offset, state = Running)
        private fun output(value: Int) = next().copy(state = Output(value))
        fun execute() =
            if (i !in instructions.indices) copy(state = Halted)
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

    private fun String.toProgram() = lines().map { line ->
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

    override fun part1(input: String): Int {
        val program = input.toProgram()
        return generateSequence(0) { it + 1 }.map { a ->
            generateSequence(Cpu(program, persistentHashMapOf("a" to a))) { it.execute() }
                .mapNotNull { if (it.state is Output) it.state.value else null }
                .take(10)
                .joinToString("")
        }.indexOf("0101010101")
    }

    override fun part2(input: String) = ""
}