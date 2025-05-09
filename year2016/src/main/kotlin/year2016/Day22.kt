package year2016

import framework.Context
import framework.Day
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toPersistentHashMap
import util.*

class Day22(context: Context) : Day by context {
    val end = ORIGIN
    val grid = lines.drop(2).associate { line ->
        val (x, y, _, used, avail) = line.allInts().toList()
        Point(x, y) to Node(used, avail)
    }

    data class Node(val used: Int, val avail: Int) {
        fun isEmpty() = used == 0
        infix fun accepts(other: Node) = other.used <= avail
        fun empty() = Node(0, avail + used)
        fun accept(other: Node) = Node(used + other.used, avail - other.used)
    }

    data class State(val grid: PersistentMap<Point, Node>, val goal: Point, val empty: Point) {
        override fun hashCode() = (goal to empty).hashCode()
        override fun equals(other: Any?) = other is State && goal == other.goal && empty == other.empty
    }

    fun State.isEnd() = goal == end
    fun State.heuristic() = 10 * (goal manhattan end) + (goal manhattan empty)
    fun State.next(): List<State> {
        val (grid, goal, empty) = this
        val emptyNode = grid.getValue(empty)
        return empty.cardinalNeighbours
            .filter { it in grid }
            .filter { emptyNode accepts grid.getValue(it) }
            .map { nextEmpty ->
                val nextGoal = if (nextEmpty == goal) empty else goal
                State(grid.swap(nextEmpty, empty), nextGoal, nextEmpty)
            }
    }

    fun PersistentMap<Point, Node>.swap(from: Point, to: Point): PersistentMap<Point, Node> {
        val nodeFrom = getValue(from)
        val nodeTo = getValue(to)
        return this + (from to nodeFrom.empty()) + (to to nodeTo.accept(nodeFrom))
    }

    fun Map<Point, Node>.minimizeSteps(): Int {
        val goal = Point(keys.maxOf { it.x }, 0)
        val empty = entries.first { (_, v) -> v.used == 0 }.key
        val initial = State(toPersistentHashMap(), goal, empty)
        val queue = priorityQueueOf(initial to 0) { it.second }
        val cost = mutableMapOf(initial to 0)

        queue.drain { (state) ->
            if (state.isEnd()) return cost.getValue(state)
            val nextCost = cost.getValue(state) + 1
            state.next().forEach { next ->
                if (next !in cost || nextCost < cost.getValue(next)) {
                    cost[next] = nextCost
                    val priority = nextCost + next.heuristic()
                    queue.add(next to priority)
                }
            }
        }

        error("This should never happen")
    }

    fun part1() = grid.values.toList().pairs().count { (a, b) -> !a.isEmpty() && b accepts a || !b.isEmpty() && a accepts b }
    fun part2() = grid.minimizeSteps()
}