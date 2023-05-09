object Day6 : Day<Int> {
    data class State(val banks: List<Int>, val seen: Map<Int, Int> = mapOf(), val cycles: Int = 0)

    private fun State.next(): State {
        val max = banks.max()
        val start = banks.indexOf(max)
        val next = (1..max).fold(banks.mapIndexed { i, b -> if (i == start) 0 else b }) { banks, offset ->
            val index = (start + offset) % banks.size
            banks.mapIndexed { i, b -> if (i == index) b + 1 else b }
        }
        return State(next, seen + (banks.hashCode() to cycles), cycles + 1)
    }

    private fun String.findLoop(): State {
        val initial = State(allInts().toList())
        return generateSequence(initial) { it.next() }.dropWhile { it.banks.hashCode() !in it.seen }.first()
    }

    override fun part1(input: String) = input.findLoop().cycles

    override fun part2(input: String) = input.findLoop().let { it.cycles - it.seen.getValue(it.banks.hashCode()) }
}