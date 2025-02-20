package year2022

import framework.Context
import framework.Day
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toPersistentList
import util.*

class Day11(context: Context) : Day by context {
    data class Monkey(
        val items: PersistentList<Long>,
        val operation: (Long) -> Long,
        val test: Int,
        val ifTrue: Int,
        val ifFalse: Int,
        val inspections: Long = 0L
    ) {
        fun accept(extra: List<Long>) = copy(items = items + extra)
        fun endRound() = copy(items = items.clear(), inspections = inspections + items.size)
    }

    val monkeys = input.split(EMPTY_LINE).map {
        val lines = it.lines()
        val items = lines[1].allInts().map { it.toLong() }.toPersistentList()
        val operation: (Long) -> Long = lines[2].split(' ').takeLast(2).let { (operator, value) ->
            when {
                value == "old" -> { x -> x * x }
                operator == "*" -> { x -> x * value.toLong() }
                else -> { x -> x + value.toLong() }
            }
        }
        val test = lines[3].allInts().first()
        val monkeyTrue = lines[4].allInts().first()
        val monkeyFalse = lines[5].allInts().first()

        Monkey(items, operation, test, monkeyTrue, monkeyFalse)
    }.toPersistentList()

    fun List<Monkey>.commonDivisor() = productOf { it.test }.toLong()
    fun List<Monkey>.business() = map { it.inspections }.sortedDescending().take(2).product()

    fun PersistentList<Monkey>.round(
        reduceItem: (Long) -> Long
    ): PersistentList<Monkey> = indices.fold(this) { monkeys, current ->
        val (items, operation, test, ifTrue, ifFalse) = monkeys[current]
        val (pass, fail) = items.map(operation).map(reduceItem).partition { it % test == 0L }
        monkeys
            .set(current, monkeys[current].endRound())
            .set(ifTrue, monkeys[ifTrue].accept(pass))
            .set(ifFalse, monkeys[ifFalse].accept(fail))
    }

    fun PersistentList<Monkey>.play(rounds: Int, reduceItem: (Long) -> Long): Long =
        generateSequence(this) { it.round(reduceItem) }.nth(rounds).business()

    fun part1() = monkeys.play(20) { it / 3 }
    fun part2() = monkeys.let { m -> m.play(10000) { it % m.commonDivisor() } }
}