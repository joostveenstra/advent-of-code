package year2017

import framework.Context
import framework.Day
import kotlin.math.sqrt

class Day23(context: Context) : Day by context {
    sealed interface Instruction
    data class Set(val to: String, val from: String) : Instruction
    data class Sub(val to: String, val from: String) : Instruction
    data class Mul(val to: String, val from: String) : Instruction
    data class Jnz(val from: String, val offset: String) : Instruction

    data class Cpu(
        val instructions: List<Instruction>,
        val registers: MutableMap<String, Long> = mutableMapOf(),
        val running: Boolean = true,
        val i: Int = 0,
        val count: Int = 0
    ) {
        fun read(key: String) = key.toLongOrNull() ?: registers.getOrDefault(key, 0)
        fun write(key: String, value: Long) = next().apply { registers[key] = value }
        fun next() = copy(i = i + 1)
        fun jump(offset: String) = copy(i = i + read(offset).toInt())
        fun execute() =
            if (i !in instructions.indices) copy(running = false)
            else with(instructions[i]) {
                when (this) {
                    is Set -> write(to, read(from))
                    is Sub -> write(to, read(to) - read(from))
                    is Mul -> write(to, read(to) * read(from)).copy(count = count + 1)
                    is Jnz -> if (read(from) != 0L) jump(offset) else next()
                }
            }
    }

    val program = lines.map { line ->
        line.split(' ').let {
            when (it[0]) {
                "set" -> Set(it[1], it[2])
                "sub" -> Sub(it[1], it[2])
                "mul" -> Mul(it[1], it[2])
                else -> Jnz(it[1], it[2])
            }
        }
    }

    fun part1() = generateSequence(Cpu(program, mutableMapOf("a" to 0L))) { it.execute() }.dropWhile { it.running }.first().count
    fun part2() = (108100..125100 step 17).count { n ->
        (2..sqrt(n.toDouble()).toInt()).any { n % it == 0 }
    }
}