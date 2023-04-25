object Day23 : Day<Int> {
    sealed interface Instruction
    data class Cpy(val from: String, val to: String) : Instruction
    data class Inc(val to: String) : Instruction
    data class Dec(val to: String) : Instruction
    data class Jnz(val from: String, val offset: String) : Instruction
    data class Tgl(val from: String) : Instruction
    data class Mul(val from: String, val to: String) : Instruction
    object Nop : Instruction

    data class Cpu(val instructions: List<Instruction>, val registers: Map<String, Int>, val i: Int = 0, val running: Boolean = true) {
        private fun read(key: String) = key.toIntOrNull() ?: registers.getOrDefault(key, 0)
        private fun write(key: String, value: Int) = next().copy(registers = registers + (key to value))
        private fun next() = copy(i = i + 1)
        private fun jump(offset: String) = copy(i = i + read(offset))
        private fun toggle(key: String): Cpu {
            val index = i + read(key)
            return if (index !in instructions.indices) next() else {
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
                val newInstructions = instructions.mapIndexed { i, op -> if (i == index) newOp else op }
                next().copy(instructions = newInstructions)
            }
        }

        fun execute() =
            if (i !in instructions.indices) copy(running = false)
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

    private fun String.toProgram() = lines().map { line ->
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

    private fun List<Instruction>.patch() = mapIndexed { i, op ->
        when (i) {
            2, 3, 4, 5, 6, 7, 8 -> Nop
            9 -> Mul("b", "a")
            12, 13 -> Nop
            14 -> Mul("2", "c")
            else -> op
        }
    }

    private fun List<Instruction>.run(a: Int) =
        generateSequence(Cpu(this, mapOf("a" to a))) { it.execute() }.dropWhile { it.running }.first().registers.getValue("a")

    override fun part1(input: String) = input.toProgram().run(7)

    override fun part2(input: String) = input.toProgram().patch().run(12)
}