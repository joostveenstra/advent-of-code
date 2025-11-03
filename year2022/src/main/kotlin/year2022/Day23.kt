package year2022

import framework.Context
import framework.Day
import util.*

class Day23(context: Context) : Day by context {
    val order = listOf(NORTH, SOUTH, WEST, EAST)

    data class State(val elves: Set<Elf>, val directions: List<Direction>, val stuck: Boolean = false)

    val elves = buildSet {
        lines.forEachIndexed { y, row ->
            row.forEachIndexed { x, col ->
                if (col == '#') add(Point(x, y))
            }
        }
    }

    fun Elf.propose(elves: Set<Elf>, directions: List<Direction>): Elf? {
        val neighboursExist = allNeighbours.map { it in elves }
        val (e, s, w, n, se, sw, nw, ne) = neighboursExist
        val (north, south, west) = order

        return if (neighboursExist.any { it })
            directions.find {
                when (it) {
                    north -> !(n || nw || ne)
                    south -> !(s || sw || se)
                    west -> !(w || nw || sw)
                    else -> !(e || ne || se)
                }
            }?.let { this + it }
        else null
    }

    fun round(state: State): State {
        val (elves, directions) = state
        val proposals = elves.associateWith { it.propose(elves, directions) }
        val occurrences = proposals.values.frequencies()
        val next = proposals.map { (elf, proposal) ->
            proposal?.let { if (occurrences[it] == 1) it else elf } ?: elf
        }.toSet()

        return State(next, directions.drop(1) + directions.first(), elves == next)
    }

    fun Set<Elf>.process(): Sequence<State> {
        val initial = State(this, order)
        return generateSequence(initial) { round(it) }
    }

    val processed = elves.process()

    fun part1(): Int {
        val end = processed.nth(10).elves

        val minX = end.minOf { it.x }
        val maxX = end.maxOf { it.x }
        val minY = end.minOf { it.y }
        val maxY = end.maxOf { it.y }

        return (maxX - minX + 1) * (maxY - minY + 1) - end.size
    }

    fun part2(): Int = processed.indexOfFirst { it.stuck }
}