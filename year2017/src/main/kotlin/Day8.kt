object Day8 : Day<Int> {
    data class State(val registers: Map<String, Int>, val max: Int)

    private fun String.execute(): State {
        val initial = State(mapOf(), 0)
        return lines().map { it.split(' ') }.fold(initial) { state, instruction ->
            val (registers, max) = state
            val (register, operation, amount) = instruction
            val (from, condition, value) = instruction.slice(4..instruction.lastIndex)
            val lhs = registers.getOrDefault(from, 0)
            val rhs = value.toInt()
            val pass = when (condition) {
                ">" -> lhs > rhs
                "<" -> lhs < rhs
                ">=" -> lhs >= rhs
                "<=" -> lhs <= rhs
                "==" -> lhs == rhs
                else -> lhs != rhs
            }
            if (pass) {
                val next = registers.getOrDefault(register, 0) + if (operation == "inc") amount.toInt() else -amount.toInt()
                State(registers + (register to next), maxOf(max, next))
            } else {
                state
            }
        }
    }

    override fun part1(input: String) = input.execute().registers.values.max()

    override fun part2(input: String) = input.execute().max
}