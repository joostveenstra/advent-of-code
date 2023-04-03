object Day11 : Day<Long> {
    data class Monkey(
        val items: List<Long>,
        val operation: (Long) -> Long,
        val test: Int,
        val ifTrue: Int,
        val ifFalse: Int,
        val inspections: Long = 0L
    ) {
        companion object {
            fun of(input: List<String>): Monkey {
                fun parseNumbers(input: String) = "\\d+".toRegex().findAll(input).map { it.value.toInt() }
                val items = parseNumbers(input[1]).map { it.toLong() }.toList()
                val operation: (Long) -> Long = input[2].split(' ').takeLast(2).let { (operator, value) ->
                    when {
                        value == "old" -> { x -> x * x }
                        operator == "*" -> { x -> x * value.toLong() }
                        else -> { x -> x + value.toLong() }
                    }
                }
                val test = parseNumbers(input[3]).first()
                val monkeyTrue = parseNumbers(input[4]).first()
                val monkeyFalse = parseNumbers(input[5]).first()

                return Monkey(items, operation, test, monkeyTrue, monkeyFalse)
            }
        }
    }

    private fun String.toMonkeys() = split(EMPTY_LINE).map { Monkey.of(it.lines()) }

    private fun List<Monkey>.commonDivisor() = productOf { it.test }.toLong()

    private fun List<Monkey>.business() = map { it.inspections }.sortedDescending().take(2).product()

    private fun List<Monkey>.round(reduceItem: (Long) -> Long): List<Monkey> = indices.fold(this) { monkeys, current ->
        val (items, operation, test, ifTrue, ifFalse, inspections) = monkeys[current]
        val (pass, fail) = items.map(operation).map(reduceItem).partition { it % test == 0L }
        monkeys.mapIndexed { index, monkey ->
            when (index) {
                current -> monkey.copy(items = emptyList(), inspections = inspections + items.size)
                ifTrue -> monkey.copy(items = monkey.items + pass)
                ifFalse -> monkey.copy(items = monkey.items + fail)
                else -> monkey
            }
        }
    }

    private fun List<Monkey>.play(rounds: Int, reduceItem: (Long) -> Long): Long =
        generateSequence(this) { it.round(reduceItem) }.drop(rounds).first().business()

    override fun part1(input: String) = input.toMonkeys().play(20) { it / 3 }

    override fun part2(input: String) = input.toMonkeys().let { m -> m.play(10000) { it % m.commonDivisor() } }
}