package year2025

import framework.Context
import framework.Day
import util.allInts
import util.mapInPlace
import util.toInt

class Day10(context: Context) : Day by context {
    class Machine(val lights: Int, val buttons: Array<IntArray>, val joltages: IntArray) {
        val masks = IntArray(buttons.size) { i -> buttons[i].fold(0) { pattern, j -> pattern or (1 shl j) } }
        val combinations = Array(1 shl joltages.size) { mutableListOf<Int>() }.apply {
            this[0].add(0)
            val end = 1 shl buttons.size
            var combination = 0
            var pattern = 0

            for (i in 1..<end) {
                val lowest = i.takeLowestOneBit()
                combination = combination xor lowest
                pattern = pattern xor masks[lowest.countTrailingZeroBits()]
                this[pattern].add(combination)
            }
        }
    }

    val machines = lines.map { l ->
        l.split(" ").let { parts ->
            val lights = parts.first().drop(1).foldIndexed(0) { i, lights, c -> lights or ((c == '#').toInt() shl i) }
            val buttons = Array(parts.size - 2) { i -> parts[i + 1].allInts().toList().toIntArray() }
            val joltages = parts.last().allInts().toList().toIntArray()
            Machine(lights, buttons, joltages)
        }
    }

    fun Machine.configureLights() = combinations[lights].minOf { it.countOneBits() }

    fun Machine.configureJoltages(): Int {
        val cache = mutableMapOf<Int, Int>()

        fun IntArray.subtract(combination: Int) = buttons.indices.all { i ->
            (combination and (1 shl i)) == 0 || buttons[i].all { --this[it] >= 0 }
        }

        fun IntArray.halve() = mapInPlace { it ushr 1 }

        fun IntArray.count(): Int = cache.getOrPut(contentHashCode()) {
            if (all { it == 0 }) 0
            else {
                val parity = foldIndexed(0) { i, parity, j -> parity or ((j and 1) shl i) }
                combinations[parity].fold(1000000) { min, combination ->
                    val count = combination.countOneBits()
                    val remaining = copyOf()
                    remaining.subtract(combination) || return@fold min
                    remaining.halve()
                    minOf(min, count + (remaining.count() shl 1))
                }
            }
        }

        return joltages.count()
    }

    fun part1() = machines.sumOf { it.configureLights() }
    fun part2() = machines.sumOf { it.configureJoltages() }
}