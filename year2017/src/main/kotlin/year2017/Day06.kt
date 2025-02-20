package year2017

import framework.Context
import framework.Day
import kotlinx.collections.immutable.*
import util.allInts

class Day06(context: Context) : Day by context {
    data class State(val banks: PersistentList<Int>, val seen: PersistentMap<Int, Int> = persistentHashMapOf(), val cycles: Int = 0)

    fun State.next(): State {
        val max = banks.max()
        val start = banks.indexOf(max)
        val next = (1..max).fold(banks.set(start, 0)) { banks, offset ->
            val index = (start + offset) % banks.size
            banks.set(index, banks[index] + 1)
        }
        return State(next, seen + (banks.hashCode() to cycles), cycles + 1)
    }

    fun String.findLoop(): State {
        val initial = State(allInts().toPersistentList())
        return generateSequence(initial) { it.next() }.dropWhile { it.banks.hashCode() !in it.seen }.first()
    }

    fun part1() = input.findLoop().cycles
    fun part2() = input.findLoop().let { it.cycles - it.seen.getValue(it.banks.hashCode()) }
}