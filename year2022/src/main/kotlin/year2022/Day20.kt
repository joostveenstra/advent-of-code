package year2022

import framework.Context
import framework.Day

class Day20(context: Context) : Day by context {
    val numbers = lines.map(String::toLong)

    fun List<Long>.decrypt(key: Long = 1, rounds: Int = 1): Long {
        val indexed = map { it * key }.withIndex()
        val mix = indexed.toMutableList()

        repeat(rounds) {
            for (n in indexed) {
                val from = mix.indexOf(n)
                mix.removeAt(from)
                val to = (from + n.value).mod(mix.size)
                mix.add(to, n)
            }
        }

        val start = mix.indexOfFirst { it.value == 0L }
        return (1000..3000 step 1000).sumOf { mix[(start + it) % mix.size].value }
    }

    fun part1() = numbers.decrypt()
    fun part2() = numbers.decrypt(811_589_153, 10)
}