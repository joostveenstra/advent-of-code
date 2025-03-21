package year2022

import framework.Context
import framework.Day
import kotlinx.collections.immutable.*
import util.dequeOf
import util.drain
import util.push

class Day16(context: Context) : Day by context {
    data class Valve(val flow: Int, val neighbours: List<String>)

    data class State(val todo: PersistentSet<String>, val visited: PersistentSet<String>, val from: String, val time: Int, val pressure: Int)

    val valves = run {
        val valves = lines.associate { line ->
            line.split("[^A-Z0-9]+".toRegex()).let {
                val (_, name, flowRate) = it
                name to Valve(flowRate.toInt(), it.drop(3))
            }
        }
        val paths = valves.mapValues { (k, _) -> valves.determineShortestPathsFrom(k) }
        val valvesToVisit = valves.filterValues { it.flow > 0 }.keys
        Triple(valves, paths, valvesToVisit)
    }

    fun Map<String, Valve>.determineShortestPathsFrom(root: String): Map<String, Int> {
        val queue = dequeOf(root)
        val visited = mutableMapOf(root to 1)

        queue.drain { valve ->
            getValue(valve).neighbours.filterNot { it in visited }.forEach { next ->
                queue.add(next)
                visited[next] = visited.getValue(valve) + 1
            }
        }

        return visited
    }

    fun determineSubSetsMaxPressure(initialTime: Int): Map<Set<String>, Int> {
        val (valves, paths, valvesToVisit) = valves
        val initial = State(valvesToVisit.toPersistentSet(), persistentSetOf(), "AA", initialTime, 0)
        val stack = dequeOf(initial)
        val maxPressure = mutableMapOf<Set<String>, Int>().withDefault { 0 }

        stack.drain { (todo, visited, from, time, pressure) ->
            maxPressure[visited] = maxOf(maxPressure.getValue(visited), pressure)
            for (next in todo) {
                val remaining = time - paths.getValue(from).getValue(next)
                if (remaining > 0) {
                    val extra = remaining * valves.getValue(next).flow
                    stack.push(State(todo - next, visited + next, next, remaining, pressure + extra))
                }
            }
        }

        return maxPressure
    }

    fun part1() = determineSubSetsMaxPressure(30).values.max()
    fun part2(): Int {
        val subSetsMax = determineSubSetsMaxPressure(26)
        val disjoint = buildList {
            for ((you, youPressure) in subSetsMax)
                for ((elephant, elephantPressure) in subSetsMax)
                    if ((you intersect elephant).isEmpty())
                        add(youPressure + elephantPressure)
        }
        return disjoint.max()
    }
}