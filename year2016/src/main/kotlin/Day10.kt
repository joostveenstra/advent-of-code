object Day10 : Day<Int> {
    data class State(val todo: Map<String, Set<Int>>, val done: Map<String, Set<Int>>, val lows: Map<String, String>, val highs: Map<String, String>)

    private fun String.toInitial(): State {
        val bots = lines().fold(emptyMap<String, Set<Int>>()) { bots, line ->
            line.match("value (\\d+) goes to (.+)".toRegex())
                ?.let { (value, bot) -> bots + (bot to bots.getOrDefault(bot, setOf()) + value.toInt()) }
                ?: bots
        }
        val (lows, highs) = lines().fold(emptyMap<String, String>() to emptyMap<String, String>()) { (lows, highs), line ->
            line.match("(.+) gives low to (.+) and high to (.+)".toRegex())
                ?.let { (bot, low, high) -> lows + (bot to low) to highs + (bot to high) }
                ?: (lows to highs)
        }
        return State(bots, mapOf(), lows, highs)
    }

    private fun State.next() = todo.entries.find { (k, chips) -> k.startsWith("bot") && chips.size == 2 }
        ?.let { (bot, chips) ->
            val low = lows.getValue(bot)
            val high = highs.getValue(bot)
            val next = todo - bot +
                    (low to todo.getOrDefault(low, setOf()) + chips.min()) +
                    (high to todo.getOrDefault(high, setOf()) + chips.max())
            State(next, done + (bot to chips), lows, highs)
        }

    private fun String.toGoal() = if (lines().size == 6) setOf(5, 2) else setOf(17, 61)

    private fun String.zoomAround() = generateSequence(toInitial()) { it.next() }.last()

    override fun part1(input: String): Int {
        val goal = input.toGoal()
        return input.zoomAround().done.entries.first { (_, chips) -> chips == goal }.key.filter { it.isDigit() }.toInt()
    }

    override fun part2(input: String): Int {
        val outputs = input.zoomAround().todo
        return listOf("output 0", "output 1", "output 2").flatMap(outputs::getValue).product()
    }
}