package year2024

import framework.Day
import util.nth
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.IntStream
import kotlin.streams.asSequence

object Day22 : Day {
    private inline fun Int.mix(f: (Int) -> Int) = this xor f(this)
    private fun Int.prune() = this and 0xffffff
    private fun Int.next() = this
        .mix { it shl 6 }.prune()
        .mix { it shr 5 }.prune()
        .mix { it shl 11 }.prune()

    private fun Int.secrets() = IntStream.iterate(this) { it.next() }.asSequence()

    private fun List<Int>.hash() = zipWithNext { a, b -> b - a }.fold(1) { hash, change -> hash * 19 + change }

    private fun String.toBuyers() = lines().map { it.toInt() }

    override fun part1(input: String) = input.toBuyers().sumOf { it.secrets().nth(2000).toLong() }

    override fun part2(input: String): Int {
        val sequences = ConcurrentHashMap<Int, Int>()
        input.toBuyers().parallelStream().forEach { buyer ->
            val seen = mutableSetOf<Int>()
            buyer.secrets().map { it % 10 }.take(2001).windowed(5).forEach { sequence ->
                val hash = sequence.hash()
                val banana = sequence.last()
                if (seen.add(hash)) sequences.merge(hash, banana, Int::plus)
            }
        }
        return sequences.values.max()
    }
}