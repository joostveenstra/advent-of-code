package year2017

import framework.Context
import framework.Day

class Day18(context: Context) : Day by context {
    sealed interface Instruction
    data class Snd(val from: String) : Instruction
    data class Set(val to: String, val from: String) : Instruction
    data class Add(val to: String, val from: String) : Instruction
    data class Mul(val to: String, val from: String) : Instruction
    data class Mod(val to: String, val from: String) : Instruction
    data class Rcv(val register: String) : Instruction
    data class Jgz(val from: String, val offset: String) : Instruction

    data class Cpu(
        val instructions: List<Instruction>,
        val rcv: (Cpu, String) -> Cpu,
        val registers: MutableMap<String, Long> = mutableMapOf(),
        val running: Boolean = true,
        val i: Int = 0,
        val received: Int = 0,
        val input: MutableList<Long> = mutableListOf(),
        val output: MutableList<Long> = mutableListOf()
    ) {
        fun read(key: String) = key.toLongOrNull() ?: registers.getOrDefault(key, 0)
        fun write(key: String, value: Long) = next().apply { registers[key] = value }
        fun next() = copy(i = i + 1)
        fun jump(offset: String) = copy(i = i + read(offset).toInt())
        fun execute() =
            if (i !in instructions.indices) copy(running = false)
            else with(instructions[i]) {
                when (this) {
                    is Snd -> next().apply { output.add(read(from)) }
                    is Set -> write(to, read(from))
                    is Add -> write(to, read(to) + read(from))
                    is Mul -> write(to, read(to) * read(from))
                    is Mod -> write(to, read(to) % read(from))
                    is Rcv -> rcv(this@Cpu, register)
                    is Jgz -> if (read(from) > 0) jump(offset) else next()
                }
            }
    }

    val program = lines.map { line ->
        line.split(' ').let {
            when (it[0]) {
                "snd" -> Snd(it[1])
                "set" -> Set(it[1], it[2])
                "add" -> Add(it[1], it[2])
                "mul" -> Mul(it[1], it[2])
                "mod" -> Mod(it[1], it[2])
                "rcv" -> Rcv(it[1])
                else -> Jgz(it[1], it[2])
            }
        }
    }

    fun part1(): Long {
        fun rcv(cpu: Cpu, register: String) = if (cpu.read(register) != 0L) cpu.copy(running = false) else cpu.next()
        return generateSequence(Cpu(program, ::rcv)) { it.execute() }.dropWhile { it.running }.first().output.last()
    }

    fun part2(): Int {
        fun rcv(cpu: Cpu, register: String) =
            if (cpu.received == cpu.input.size) cpu.copy(running = false)
            else cpu.write(register, cpu.input[cpu.received]).copy(received = cpu.received + 1)

        fun Pair<Cpu, Cpu>.next() = let { (a, b) -> a.copy(input = b.output).execute() to b.copy(input = a.output).execute() }

        val initial = Cpu(program, ::rcv, mutableMapOf("p" to 0)) to Cpu(program, ::rcv, mutableMapOf("p" to 1))
        return generateSequence(initial) { it.next() }.dropWhile { (a, b) -> a.running || b.running }.first().second.output.size
    }
}