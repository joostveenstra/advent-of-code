package year2022

import framework.Day

object Day20 : Day<Long> {
    private fun String.toNumbers() = lines().map(String::toLong)

    private fun List<Long>.decrypt(key: Long = 1, rounds: Int = 1): Long {
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

    override fun part1(input: String) = input.toNumbers().decrypt()

    override fun part2(input: String) = input.toNumbers().decrypt(811_589_153, 10)
}