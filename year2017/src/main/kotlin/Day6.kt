import kotlinx.collections.immutable.*

object Day6 : Day<Int> {
    data class State(val banks: PersistentList<Int>, val seen: PersistentMap<Int, Int> = persistentHashMapOf(), val cycles: Int = 0)

    private fun State.next(): State {
        val max = banks.max()
        val start = banks.indexOf(max)
        val next = (1..max).fold(banks.set(start, 0)) { banks, offset ->
            val index = (start + offset) % banks.size
            banks.set(index, banks[index] + 1)
        }
        return State(next, seen + (banks.hashCode() to cycles), cycles + 1)
    }

    private fun String.findLoop(): State {
        val initial = State(allInts().toPersistentList())
        return generateSequence(initial) { it.next() }.dropWhile { it.banks.hashCode() !in it.seen }.first()
    }

    override fun part1(input: String) = input.findLoop().cycles

    override fun part2(input: String) = input.findLoop().let { it.cycles - it.seen.getValue(it.banks.hashCode()) }
}