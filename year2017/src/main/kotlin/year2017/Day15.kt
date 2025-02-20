package year2017

import framework.Context
import framework.Day
import util.allInts

class Day15(context: Context) : Day by context {
    val generators = input.allInts().map { it.toLong() }.toList().let { (a, b) ->
        generator(a, 16807) to generator(b, 48271)
    }

    fun generator(start: Long, factor: Long) = generateSequence(start) { (it * factor) % 2147483647 }.drop(1)

    fun judge(a: Long, b: Long) = (a and 0xFFFF) == (b and 0xFFFF)

    fun part1() = generators.let { (a, b) ->
        (a zip b).take(40000000).count { (a, b) -> judge(a, b) }
    }

    fun part2() = generators.let { (a, b) ->
        (a.filter { it % 4 == 0L } zip b.filter { it % 8 == 0L }).take(5000000).count { (a, b) -> judge(a, b) }
    }
}