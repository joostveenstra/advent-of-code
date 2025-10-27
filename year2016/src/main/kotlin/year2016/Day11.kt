package year2016

import framework.Context
import framework.Day
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import util.drain
import util.findAll
import util.priorityQueueOf

class Day11(context: Context) : Day by context {
    data class Resources(val chips: Int, val generators: Int) {
        operator fun plus(other: Resources) = Resources(chips + other.chips, generators + other.generators)
        operator fun minus(other: Resources) = Resources(chips - other.chips, generators - other.generators)
    }

    data class State(val floor: Int, val floors: PersistentList<Resources>)

    val floors = lines.map { line ->
        val chips = line.findAll("microchip".toRegex()).count()
        val generators = line.findAll("generator".toRegex()).count()
        Resources(chips, generators)
    }
    val initial = State(0, floors.toPersistentList())
    val expanded = initial.copy(floors = initial.floors.set(0, initial.floors[0] + Resources(2, 2)))

    val moves = listOf(
        Resources(2, 0),
        Resources(1, 0),
        Resources(1, 1),
        Resources(0, 1),
        Resources(0, 2)
    )

    val adjacent = mapOf(
        0 to listOf(1),
        1 to listOf(0, 2),
        2 to listOf(1, 3),
        3 to listOf(2)
    )

    fun State.isValid() = floors.all { it.chips >= 0 && it.generators >= 0 && (it.generators == 0 || it.chips <= it.generators) }
    fun State.isEnd(total: Int) = floor == 3 && floors[3].let { it.chips + it.generators } == total
    fun State.next() = buildList {
        for (next in adjacent.getValue(floor)) for (move in moves)
            State(next, floors.set(floor, floors[floor] - move).set(next, floors[next] + move))
                .takeIf { it.isValid() }
                ?.let { add(it) }
    }

    fun minimize(initial: State): Int {
        val total = initial.floors.sumOf { it.chips + it.generators }
        val queue = priorityQueueOf(initial to 0) { it.second }
        val visited = mutableSetOf(initial)

        queue.drain { (current, cost) ->
            if (current.isEnd(total)) return cost
            current.next().forEach { next ->
                if (visited.add(next)) queue.add(next to cost + 1)
            }
        }

        error("This should never happen")
    }

    fun part1() = minimize(initial)
    fun part2() = minimize(expanded)
}