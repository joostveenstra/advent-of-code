package year2024

import framework.Context
import framework.Day
import util.nth
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.IntStream
import kotlin.streams.asSequence

class Day22(context: Context) : Day by context {
    val buyers = lines.map { it.toInt() }

    fun Int.mix(f: (Int) -> Int) = this xor f(this)
    fun Int.prune() = this and 0xffffff
    fun Int.next() = this
        .mix { it shl 6 }.prune()
        .mix { it shr 5 }.prune()
        .mix { it shl 11 }.prune()

    fun Int.secrets() = IntStream.iterate(this) { it.next() }.asSequence()

    fun List<Int>.hash() = zipWithNext { a, b -> b - a }.fold(1) { hash, change -> hash * 19 + change }

    fun part1() = buyers.sumOf { it.secrets().nth(2000).toLong() }
    fun part2() = run {
        val sequences = ConcurrentHashMap<Int, Int>()
        buyers.parallelStream().forEach { buyer ->
            val seen = mutableSetOf<Int>()
            buyer.secrets().map { it % 10 }.take(2001).windowed(5).forEach { sequence ->
                val hash = sequence.hash()
                val banana = sequence.last()
                if (seen.add(hash)) sequences.merge(hash, banana, Int::plus)
            }
        }
        sequences.values.max()
    }
}