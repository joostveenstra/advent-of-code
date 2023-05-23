object Day25 : Day<Int> {
    data class Rule(val write: Boolean, val move: Int, val next: String)
    data class TuringMachine(val tape: MutableSet<Int>, val cursor: Int, val state: String)

    private fun parse(input: String): Triple<String, Int, Map<Pair<String, Boolean>, Rule>> {
        val tokens = input.lines().map { it.dropLast(1).split(' ') }
        val start = tokens[0].last()
        val total = tokens[1][5].toInt()
        val rules = tokens.drop(2).chunked(10)
            .map { chunk ->
                val state = chunk[1].last()
                listOf(
                    (state to false) to parseRule(chunk.drop(3)),
                    (state to true) to parseRule(chunk.drop(7))
                )
            }
            .flatten()
            .toMap()
        return Triple(start, total, rules)
    }

    private fun parseRule(chunk: List<List<String>>): Rule {
        val write = chunk[0].last() == "1"
        val move = if (chunk[1].last() == "left") -1 else 1
        val next = chunk[2].last()
        return Rule(write, move, next)
    }

    private fun TuringMachine.step(rules: Map<Pair<String, Boolean>, Rule>): TuringMachine {
        val rule = rules.getValue(state to (cursor in tape))
        if (rule.write) tape += cursor else tape -= cursor
        return TuringMachine(tape, cursor + rule.move, rule.next)
    }

    override fun part1(input: String): Int {
        val (start, total, rules) = parse(input)
        val initial = TuringMachine(mutableSetOf(), 0, start)
        return generateSequence(initial) { it.step(rules) }.drop(total).first().tape.size
    }

    override fun part2(input: String) = 0
}