package year2015

import framework.Day
import util.EMPTY_LINE
import util.drain
import util.priorityQueueOf

typealias Rules = List<Pair<String, List<String>>>

object Day19 : Day {
    private fun String.toMedicine() = split(EMPTY_LINE).last()

    private fun String.toRules(reversed: Boolean): Rules = split(EMPTY_LINE).first().lines()
        .map { it.split(" => ").let { (k, v) -> if (reversed) v to k else k to v } }
        .groupBy { it.first }
        .map { (k, v) -> k to v.map { it.second } }

    private fun String.nextMolecules(rules: Rules): Set<String> = rules.flatMap { (key, values) ->
        key.toRegex().findAll(this).flatMap { match -> values.map { this.replaceRange(match.range, it) } }
    }.toSet()

    private fun aStar(rules: Rules, start: String): Int {
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

    override fun part1(input: String): Int {
        val rules = input.toRules(false)
        val medicine = input.toMedicine()
        return medicine.nextMolecules(rules).size
    }

    override fun part2(input: String): Int {
        val rules = input.toRules(true)
        val medicine = input.toMedicine()
        return aStar(rules, medicine)
    }
}