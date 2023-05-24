package year2017

import framework.Day
import kotlin.math.sqrt

object Day23 : Day<Int> {
    sealed interface Instruction
    data class Set(val to: String, val from: String) : Instruction
    data class Sub(val to: String, val from: String) : Instruction
    data class Mul(val to: String, val from: String) : Instruction
    data class Jnz(val from: String, val offset: String) : Instruction

    data class Cpu(
        val instructions: List<Instruction>,
        val registers: Map<String, Long> = mapOf(),
        val running: Boolean = true,
        val i: Int = 0,
        val count: Int = 0
    ) {
        private fun read(key: String) = key.toLongOrNull() ?: registers.getOrDefault(key, 0)
        private fun write(key: String, value: Long) = next().copy(registers = registers + (key to value))
        private fun next() = copy(i = i + 1)
        private fun jump(offset: String) = copy(i = i + read(offset).toInt())
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

    private fun String.toProgram() = lines().map { line ->
        line.split(' ').let {
            when (it[0]) {
                "set" -> Set(it[1], it[2])
                "sub" -> Sub(it[1], it[2])
                "mul" -> Mul(it[1], it[2])
                else -> Jnz(it[1], it[2])
            }
        }
    }

    override fun part1(input: String) = generateSequence(Cpu(input.toProgram(), mapOf("a" to 0L))) { it.execute() }.dropWhile { it.running }.first().count

    override fun part2(input: String): Int {
        fun composite(n: Int): Boolean = (2..sqrt(n.toDouble()).toInt()).any { n % it == 0 }
        return (108100..125100 step 17).count(::composite)
    }
}