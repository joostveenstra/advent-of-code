package year2015

import framework.Context
import framework.Day
import util.allInts

class Day14(context: Context) : Day by context {
    data class Reindeer(val speed: Int, val fly: Int, val rest: Int) {
        fun distance(time: Int): Int {
            val total = fly + rest
            val complete = time / total
            val partial = minOf(time % total, fly)
            return speed * (complete * fly + partial)
        }
    }

    val seconds = if (isExample) 1000 else 2503
    val reindeer = lines.map { line ->
        line.allInts().toList().let { (speed, fly, rest) -> Reindeer(speed, fly, rest) }
    }

    fun part1() = reindeer.maxOf { it.distance(seconds) }
    fun part2() = (1..seconds).fold(List(reindeer.size) { 0 }) { state, time ->
        val distances = reindeer.map { it.distance(time) }
        distances.map { if (it == distances.max()) 1 else 0 }.zip(state) { a, b -> a + b }
    }.max()
}