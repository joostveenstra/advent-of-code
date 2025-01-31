package year2015

import framework.Day

object Day17 : Day {
    private fun String.toCombinations(): Sequence<List<Int>> {
        val containers = lines().map { it.toInt() }
        val target = if (containers.size == 5) 25 else 150

        fun List<Int>.combinationsOfSize(target: Int): Sequence<List<Int>> = sequence {
            forEachIndexed { index, container ->
                if (target - container == 0)
                    yield(listOf(container))
                if (container < target) {
                    for (tail in subList(index + 1, size).combinationsOfSize(target - container)) {
                        yield(listOf(container) + tail)
                    }
                }
            }
        }

        return containers.combinationsOfSize(target)
    }

    override fun part1(input: String) = input.toCombinations().count()

    override fun part2(input: String): Int {
        val combinations = input.toCombinations().groupBy { it.size }
        return combinations.getValue(combinations.keys.min()).size
    }
}