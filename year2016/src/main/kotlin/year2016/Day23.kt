package year2016

import framework.Context
import framework.Day

class Day23(context: Context) : Day by context {
    sealed interface Instruction
    data class Cpy(val from: String, val to: String) : Instruction
    data class Inc(val to: String) : Instruction
    data class Dec(val to: String) : Instruction
    data class Jnz(val from: String, val offset: String) : Instruction
    data class Tgl(val from: String) : Instruction
    data class Mul(val from: String, val to: String) : Instruction
    object Nop : Instruction

    data class Cpu(val instructions: MutableList<Instruction>, val registers: MutableMap<String, Int>, var i: Int = 0, var running: Boolean = true) {
        fun read(key: String) = key.toIntOrNull() ?: registers.getOrDefault(key, 0)
        fun write(key: String, value: Int) = next().apply { registers[key] = value }
        fun next() = apply { i += 1 }
        fun jump(offset: String) = apply { i += read(offset) }
        fun toggle(key: String) {
            val index = i + read(key)
            if (index !in instructions.indices) next() else {
                val newOp = with(instructions[index]) {
                    when (this) {
                        is Inc -> Dec(to)
                        is Dec -> Inc(to)
                        is Tgl -> Inc(from)
                        is Jnz -> Cpy(from, offset)
                        is Cpy -> Jnz(from, to)
                        is Mul -> Jnz(from, to)
                        is Nop -> Nop
                    }
                }
                next()
                instructions[index] = newOp
            }
        }

        fun execute() =
            if (i !in instructions.indices) running = false
            else with(instructions[i]) {
                when (this) {
                    is Cpy -> write(to, read(from))
                    is Inc -> write(to, read(to) + 1)
                    is Dec -> write(to, read(to) - 1)
                    is Jnz -> if (read(from) != 0) jump(offset) else next()
                    is Tgl -> toggle(from)
                    is Mul -> write(to, read(from) * read(to))
                    is Nop -> next()
                }
            }
    }

    fun List<Instruction>.patch() = mapIndexed { i, op ->
        when (i) {
            2, 3, 4, 5, 6, 7, 8 -> Nop
            9 -> Mul("b", "a")
            12, 13 -> Nop
            14 -> Mul("2", "c")
            else -> op
        }
    }

    fun List<Instruction>.run(a: Int) = with(Cpu(toMutableList(), mutableMapOf("a" to a))) {
        while (running) execute()
        registers.getValue("a")
    }

    val program = lines.map { line ->
        line.split(' ').let {
            when (it[0]) {
                "cpy" -> Cpy(it[1], it[2])
                "inc" -> Inc(it[1])
                "dec" -> Dec(it[1])
                "jnz" -> Jnz(it[1], it[2])
                else -> Tgl(it[1])
            }
        }
    }

    fun part1() = program.run(7)
    fun part2() = program.patch().run(12)
}