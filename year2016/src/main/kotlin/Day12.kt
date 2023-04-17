typealias Program = List<Day12.Instruction>

object Day12 : Day<Int> {
    sealed interface Instruction
    data class Cpy(val from: String, val to: String) : Instruction
    data class Inc(val to: String) : Instruction
    data class Dec(val to: String) : Instruction
    data class Jnz(val from: String, val offset: Int) : Instruction

    data class Cpu(val instructions: Program, val registers: Map<String, Int>, val i: Int = 0, val running: Boolean = true) {
        private fun read(key: String) = key.toIntOrNull() ?: registers.getOrDefault(key, 0)
        private fun write(key: String, value: Int) = next().copy(registers = registers + (key to value))
        private fun next() = copy(i = i + 1)
        private fun jump(offset: Int) = copy(i = i + offset)
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

    private fun String.toProgram() = lines().map { line ->
        line.split(' ').let {
            when (it[0]) {
                "cpy" -> Cpy(it[1], it[2])
                "inc" -> Inc(it[1])
                "dec" -> Dec(it[1])
                else -> Jnz(it[1], it[2].toInt())
            }
        }
    }

    private fun Program.run(c: Int) = generateSequence(Cpu(this, mapOf("c" to c))) { it.execute() }.dropWhile { it.running }.first().registers.getValue("a")

    override fun part1(input: String) = input.toProgram().run(0)

    override fun part2(input: String) = input.toProgram().run(1)
}