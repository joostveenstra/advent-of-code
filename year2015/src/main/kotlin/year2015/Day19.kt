package year2015

import framework.Context
import framework.Day
import util.EMPTY_LINE
import util.drain
import util.priorityQueueOf

typealias Rules = List<Pair<String, List<String>>>

class Day19(context: Context) : Day by context {
    val split = input.split(EMPTY_LINE)
    val rules = split.first().toRules(false)
    val reversed = split.first().toRules(true)
    val medicine = split.last()

    fun String.toRules(reversed: Boolean) = lines()
        .map { it.split(" => ").let { (k, v) -> if (reversed) v to k else k to v } }
        .groupBy { it.first }
        .map { (k, v) -> k to v.map { it.second } }

    fun String.nextMolecules(rules: Rules) = rules.flatMap { (key, values) ->
        key.toRegex().findAll(this).flatMap { match -> values.map { this.replaceRange(match.range, it) } }
    }.toSet()

    fun aStar(rules: Rules, start: String): Int {
        val goal = "e"
        val queue = priorityQueueOf(start to 0) { it.second }
        val visited = mutableMapOf(start to 0)

        queue.drain { (current) ->
            val cost = visited.getValue(current)
            if (current == goal) return cost
            val nextCost = cost + 1

            current.nextMolecules(rules).forEach { next ->
                if (next !in visited || nextCost < visited.getValue(next)) {
                    visited[next] = nextCost
                    val priority = nextCost + next.length
                    queue += next to priority
                }
            }
        }

        error("This should never happen")
    }

    fun part1() = medicine.nextMolecules(rules).size
    fun part2() = aStar(reversed, medicine)
}