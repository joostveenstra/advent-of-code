package year2016

import framework.Context
import framework.Day
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import util.dequeOf
import util.drain
import util.findAll

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
    fun State.isEnd() = floor == 3 && floors.take(3).all { it.chips == 0 && it.generators == 0 }
    fun State.next() = buildList {
        for (next in adjacent.getValue(floor)) for (move in moves)
            add(State(next, floors.set(floor, floors[floor] - move).set(next, floors[next] + move)))
    }

    fun minimize(initial: State): Int {
        val queue = dequeOf(initial)
        val visited = mutableMapOf(initial to 0)

        queue.drain { current ->
            val cost = visited.getValue(current) + 1
            current.next().filter { it.isValid() }.forEach { next ->
                if (next !in visited || cost < visited.getValue(next)) {
                    visited[next] = cost
                    queue += next
                }
            }
        }

        return visited.entries.first { (k, _) -> k.isEnd() }.value
    }

    fun part1() = minimize(initial)
    fun part2() = minimize(expanded)
}