package year2016

import framework.Context
import framework.Day
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.plus

class Day12(context: Context) : Day by context {
    sealed interface Instruction
    data class Cpy(val from: String, val to: String) : Instruction
    data class Inc(val to: String) : Instruction
    data class Dec(val to: String) : Instruction
    data class Jnz(val from: String, val offset: Int) : Instruction

    data class Cpu(val instructions: List<Instruction>, val registers: PersistentMap<String, Int>, val i: Int = 0, val running: Boolean = true) {
        fun read(key: String) = key.toIntOrNull() ?: registers.getOrDefault(key, 0)
        fun write(key: String, value: Int) = next().copy(registers = registers + (key to value))
        fun next() = copy(i = i + 1)
        fun jump(offset: Int) = copy(i = i + offset)
        fun execute() =
            if (i !in instructions.indices) copy(running = false)
            else with(instructions[i]) {
                when (this) {
                    is Cpy -> write(to, read(from))
                    is Inc -> write(to, read(to) + 1)
                    is Dec -> write(to, read(to) - 1)
                    is Jnz -> if (read(from) != 0) jump(offset) else next()
                }
            }
    }

    fun List<Instruction>.run(c: Int) =
        generateSequence(Cpu(this, persistentHashMapOf("c" to c))) { it.execute() }.dropWhile { it.running }.first().registers.getValue("a")

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