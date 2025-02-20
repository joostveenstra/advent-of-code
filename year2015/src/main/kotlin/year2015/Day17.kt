package year2015

import framework.Context
import framework.Day

class Day17(context: Context) : Day by context {
    val combinations = lines.run {
        val containers = map { it.toInt() }
        val target = if (isExample) 25 else 150

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

        containers.combinationsOfSize(target)
    }

    val grouped = combinations.groupBy { it.size }

    fun part1() = combinations.count()
    fun part2() = grouped.getValue(grouped.keys.min()).size
}