package year2015

import framework.Context
import framework.Day

class Day23(context: Context) : Day by context {
    sealed interface Instruction
    data class Hlf(val register: String) : Instruction
    data class Tpl(val register: String) : Instruction
    data class Inc(val register: String) : Instruction
    data class Jmp(val offset: Int) : Instruction
    data class Jie(val register: String, val offset: Int) : Instruction
    data class Jio(val register: String, val offset: Int) : Instruction

    data class Cpu(val instructions: List<Instruction>, val a: Int = 0, val b: Int = 0, val i: Int = 0, val running: Boolean = true) {
        fun read(register: String) = if (register == "a") a else b
        fun write(register: String, op: (Int) -> Int) = next().run { if (register == "a") copy(a = op(a)) else copy(b = op(b)) }
        fun next() = copy(i = i + 1)
        fun jump(offset: Int) = copy(i = i + offset)
        fun execute() =
            if (i !in instructions.indices) copy(running = false)
            else with(instructions[i]) {
                when (this) {
                    is Hlf -> write(register) { it / 2 }
                    is Tpl -> write(register) { it * 3 }
                    is Inc -> write(register) { it + 1 }
                    is Jmp -> jump(offset)
                    is Jie -> if (read(register) % 2 == 0) jump(offset) else next()
                    is Jio -> if (read(register) == 1) jump(offset) else next()
                }
            }
    }

    fun List<Instruction>.run(a: Int) = generateSequence(Cpu(this, a)) { it.execute() }.dropWhile { it.running }.first().b

    val program = lines.map { line ->
        line.split("[, ]+".toRegex()).let {
            when (it[0]) {
                "hlf" -> Hlf(it[1])
                "tpl" -> Tpl(it[1])
                "inc" -> Inc(it[1])
                "jmp" -> Jmp(it[1].toInt())
                "jie" -> Jie(it[1], it[2].toInt())
                else -> Jio(it[1], it[2].toInt())
            }
        }
    }

    fun part1() = program.run(0)
    fun part2() = program.run(1)
}