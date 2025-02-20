package year2016

import framework.Context
import framework.Day
import util.match
import util.product

class Day10(context: Context) : Day by context {
    data class State(val todo: Map<String, Set<Int>>, val done: Map<String, Set<Int>>, val lows: Map<String, String>, val highs: Map<String, String>)

    val initial = run {
        val bots = lines.fold(mapOf<String, Set<Int>>()) { bots, line ->
            line.match("value (\\d+) goes to (.+)".toRegex())
                ?.let { (value, bot) -> bots + (bot to bots.getOrDefault(bot, setOf()) + value.toInt()) }
                ?: bots
        }
        val (lows, highs) = lines.fold(mapOf<String, String>() to mapOf<String, String>()) { (lows, highs), line ->
            line.match("(.+) gives low to (.+) and high to (.+)".toRegex())
                ?.let { (bot, low, high) -> lows + (bot to low) to highs + (bot to high) }
                ?: (lows to highs)
        }
        State(bots, mapOf(), lows, highs)
    }

    fun State.next() = todo.entries.find { (k, chips) -> k.startsWith("bot") && chips.size == 2 }
        ?.let { (bot, chips) ->
            val low = lows.getValue(bot)
            val high = highs.getValue(bot)
            val next = todo - bot +
                    (low to todo.getOrDefault(low, setOf()) + chips.min()) +
                    (high to todo.getOrDefault(high, setOf()) + chips.max())
            State(next, done + (bot to chips), lows, highs)
        }

    fun zoomAround() = generateSequence(initial) { it.next() }.last()

    val goal = if (isExample) setOf(5, 2) else setOf(17, 61)
    val end = zoomAround()

    fun part1() = end.done.entries.first { (_, chips) -> chips == goal }.key.filter { it.isDigit() }.toInt()
    fun part2() = listOf("output 0", "output 1", "output 2").flatMap(end.todo::getValue).product()
}