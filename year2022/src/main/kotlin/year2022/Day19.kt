package year2022

import framework.Context
import framework.Day
import util.*

class Day19(context: Context) : Day by context {
    data class Resources(val ore: Int = 0, val clay: Int = 0, val obsidian: Int = 0, val geode: Int = 0) {
        operator fun plus(other: Resources) = Resources(ore + other.ore, clay + other.clay, obsidian + other.obsidian, geode + other.geode)
        operator fun minus(other: Resources) = Resources(ore - other.ore, clay - other.clay, obsidian - other.obsidian, geode - other.geode)
        operator fun times(n: Int) = Resources(ore * n, clay * n, obsidian * n, geode * n)
    }

    data class State(val resources: Resources, val bots: Resources, val time: Int)

    data class Blueprint(val id: Int, val oreCost: Resources, val clayCost: Resources, val obsidianCost: Resources, val geodeCost: Resources) {
        companion object {
            fun of(input: String) = input.allInts().toList().let {
                val oreCost = Resources(ore = it[1])
                val clayCost = Resources(ore = it[2])
                val obsidianCost = Resources(ore = it[3], clay = it[4])
                val geodeCost = Resources(ore = it[5], obsidian = it[6])
                Blueprint(it[0], oreCost, clayCost, obsidianCost, geodeCost)
            }
        }

        fun determineMaxGeodesCracked(initialTime: Int): Int {
            val oreBot = Resources(ore = 1)
            val clayBot = Resources(clay = 1)
            val obsidianBot = Resources(obsidian = 1)
            val geodeBot = Resources(geode = 1)

            val initial = State(Resources(), oreBot, initialTime)
            val stack = dequeOf(initial)
            var best = 0

            fun Resources.waitFor(bots: Resources): Int {
                val waitOre = if (ore > 0) (ore - 1) / bots.ore + 1 else 0
                val waitClay = if (clay > 0) (clay - 1) / bots.clay + 1 else 0
                val waitObsidian = if (obsidian > 0) (obsidian - 1) / bots.obsidian + 1 else 0
                val waitGeode = if (geode > 0) (geode - 1) / bots.geode + 1 else 0
                return maxOf(waitOre, waitClay, waitObsidian, waitGeode)
            }

            fun State.buildBot(bot: Resources, cost: Resources): State {
                val needed = cost - resources
                val wait = needed.waitFor(bots)
                val spent = minOf(time, wait + 1)
                return State(resources - cost + bots * spent, bots + bot, time - spent)
            }

            fun State.isEnd() = time == 0
            fun State.canBeatBest() = resources.geode + (0 until time).sumOf { bots.geode + it } > best

            fun State.nextStates() = sequence {
                if (bots.obsidian > 0) yield(buildBot(geodeBot, geodeCost))
                if (bots.obsidian < geodeCost.obsidian && bots.clay > 0) yield(buildBot(obsidianBot, obsidianCost))
                if (bots.clay < obsidianCost.clay && bots.ore > 0) yield(buildBot(clayBot, clayCost))
                if (bots.ore < maxOf(clayCost.ore, obsidianCost.ore, geodeCost.ore)) yield(buildBot(oreBot, oreCost))
            }

            stack.drain { state ->
                with(state) {
                    best = maxOf(best, resources.geode)
                    if (!isEnd() && canBeatBest()) nextStates().forEach { stack.push(it) }
                }
            }

            return best
        }
    }

    val blueprints = lines.map(Blueprint::of)

    fun part1() = blueprints.sumOf { it.id * it.determineMaxGeodesCracked(24) }
    fun part2() = blueprints.take(3).productOf { it.determineMaxGeodesCracked(32) }
}