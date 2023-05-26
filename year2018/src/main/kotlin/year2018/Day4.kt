package year2018

import framework.Day
import util.allInts

object Day4 : Day<Int> {
    private fun String.toGuards(): Map<Int, Map<Int, Int>> {
        val (guards) = lines().sorted().fold(Triple(mapOf<Int, List<Int>>(), -1, -1)) { (guards, id, start), next ->
            val data = next.allInts().toList()
            val minute = data[4]
            when {
                next.endsWith("begins shift") -> Triple(guards, data[5], start)
                next.endsWith("falls asleep") -> Triple(guards, id, minute)
                else -> Triple(guards + (id to guards.getOrDefault(id, listOf()) + (start until minute)), id, start)
            }
        }
        return guards.mapValues { (_, v) -> v.groupingBy { it }.eachCount() }
    }

    private fun String.analyze(strategy: (Iterable<Int>) -> Int): Int {
        val (id, minutes) = toGuards().maxBy { (_, minutes) -> strategy(minutes.values) }
        val (minute) = minutes.maxBy { it.value }
        return id * minute
    }

    override fun part1(input: String) = input.analyze { it.sum() }

    override fun part2(input: String) = input.analyze { it.max() }
}