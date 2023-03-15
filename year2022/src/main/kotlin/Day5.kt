object Day5 : Day<String> {
    data class Procedure(val amount: Int, val from: Int, val to: Int)

    private fun parseInput(input: String): Pair<List<Deque<Char>>, List<Procedure>> {
        val (stacks, procedures) = input.split(EMPTY_LINE).map { it.lines() }
        return parseStacks(stacks) to parseProcedures(procedures)
    }

    private fun parseStacks(input: List<String>): List<Deque<Char>> {
        val crateLines = input.dropLast(1)
        return (1..crateLines.last().length step 4).map { index ->
            crateLines.mapNotNull { it.getOrNull(index) }.filterNot { it == ' ' }.let(::Deque)
        }
    }

    private fun parseProcedures(input: List<String>) = input.map { line ->
        val (amount, from, to) = line.split("\\D+".toRegex()).drop(1).map { it.toInt() }
        Procedure(amount, from - 1, to - 1)
    }

    private fun moveCrates(input: String, reversed: Boolean): String {
        val (stacks, procedures) = parseInput(input)
        procedures.forEach { (amount, from, to) ->
            val cratesToMove = (1..amount).map { stacks[from].pop() }
            stacks[to].addAll(0, if (reversed) cratesToMove.asReversed() else cratesToMove)
        }
        return stacks.map { it.peek() }.joinToString("")
    }

    override fun part1(input: String) = moveCrates(input, true)

    override fun part2(input: String) = moveCrates(input, false)
}