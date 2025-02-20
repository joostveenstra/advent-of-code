package year2018

import framework.Context
import framework.Day
import util.match

class Day07(context: Context) : Day by context {
    val instructions = lines.run {
        val regex = "Step (\\w) must be finished before step (\\w) can begin.".toRegex()
        fold(mapOf<String, List<String>>()) { steps, line ->
            line.match(regex)?.let { (first, second) ->
                steps + (first to steps.getOrDefault(first, listOf())) + (second to steps.getOrDefault(second, listOf()) + first)
            } ?: throw IllegalArgumentException("Illegal instruction: $line")
        }
    }

    fun part1(): String {
        tailrec fun path(remaining: Map<String, List<String>>, done: List<String>): String = when {
            remaining.isEmpty() -> done.joinToString("")
            else -> {
                val next = remaining.mapNotNull { (k, v) -> if (v.isEmpty()) k else null }.min()
                val nextRemaining = (remaining - next).mapValues { (_, v) -> v - next }
                path(nextRemaining, done + next)
            }
        }

        return path(instructions, listOf())
    }

    fun part2(): Int {
        val (workers, duration) = if (isExample) 2 to 0 else 5 to 60

        data class Task(val step: String, val remaining: Int) {
            fun next() = Task(step, remaining - 1)
        }

        tailrec fun time(remaining: Map<String, List<String>>, active: List<Task>, time: Int): Int = when {
            remaining.isEmpty() && active.isEmpty() -> time
            active.size < workers && remaining.values.any { it.isEmpty() } -> {
                val next = remaining.mapNotNull { (k, v) -> if (v.isEmpty()) k else null }.min()
                val task = Task(next, duration + (next.first() - 'A'))
                time(remaining - next, active + task, time)
            }
            else -> {
                val (done, todo) = active.partition { it.remaining == 0 }
                val stepsDone = done.map { it.step }.toSet()
                val nextRemaining = remaining.mapValues { (_, v) -> v - stepsDone }
                val nextActive = todo.map { it.next() }
                time(nextRemaining, nextActive, time + 1)
            }
        }

        return time(instructions, listOf(), 0)
    }
}