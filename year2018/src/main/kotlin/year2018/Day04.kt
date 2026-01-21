package year2018

import framework.Context
import framework.Day
import util.allInts
import util.frequencies

class Day04(context: Context) : Day by context {
    val guards = run {
        val (guards) = lines.sorted().fold(Triple(mapOf<Int, List<Int>>(), -1, -1)) { (guards, id, start), next ->
            val data = next.allInts().toList()
            val minute = data[4]
            when {
                next.endsWith("begins shift") -> Triple(guards, data[5], start)
                next.endsWith("falls asleep") -> Triple(guards, id, minute)
                else -> Triple(guards + (id to guards.getOrDefault(id, listOf()) + (start..<minute)), id, start)
            }
        }
        guards.mapValues { (_, v) -> v.frequencies() }
    }

    fun analyze(strategy: (Iterable<Int>) -> Int): Int {
        val (id, minutes) = guards.maxBy { (_, minutes) -> strategy(minutes.values) }
        val (minute) = minutes.maxBy { it.value }
        return id * minute
    }

    fun part1() = analyze { it.sum() }
    fun part2() = analyze { it.max() }
}