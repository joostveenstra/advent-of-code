package year2023

import framework.Day
import util.lcm

object Day20 : Day<Long> {
    sealed interface Module {
        val name: String
        val outputs: List<String>
        fun receive(source: String, pulse: Boolean): List<State> = send(pulse)
        fun send(pulse: Boolean): List<State> = outputs.map { State(name, it, pulse) }
    }

    class Broadcaster(override val name: String, override val outputs: List<String>) : Module

    class FlipFlop(override val name: String, override val outputs: List<String>, private var on: Boolean = false) : Module {
        override fun receive(source: String, pulse: Boolean) = if (pulse) listOf() else apply { on = !on }.send(on)
    }

    class Conjunction(override val name: String, override val outputs: List<String>, val memory: MutableMap<String, Boolean>) : Module {
        override fun receive(source: String, pulse: Boolean) = apply { memory[source] = pulse }.send(!memory.all { it.value })
    }

    data class State(val source: String, val destination: String, val pulse: Boolean)

    private fun String.toModules() = lines().map { line ->
        line.split(" -> ").let { (a, b) ->
            val name = a.drop(1)
            val outputs = b.split(", ")
            when (a.first()) {
                '%' -> FlipFlop(name, outputs)
                '&' -> Conjunction(name, outputs, mutableMapOf())
                else -> Broadcaster(a, outputs)
            }
        }
    }.also { modules ->
        val conjunctions = modules.filterIsInstance<Conjunction>().associateBy { it.name }
        modules.forEach { module ->
            module.outputs.forEach { output -> conjunctions[output]?.memory?.set(module.name, false) }
        }
    }.associateBy { it.name }

    private fun pressButton(
        modules: Map<String, Module>,
        parents: Set<String> = setOf(),
        pressed: Long = 0L,
        cycles: Map<String, Long> = mapOf()
    ): Triple<Long, Long, Map<String, Long>> {
        fun State.next(): List<State> = modules[destination]?.receive(source, pulse) ?: listOf()
        tailrec fun List<State>.propagate(low: Long, high: Long, cycles: Map<String, Long>): Triple<Long, Long, Map<String, Long>> =
            if (isEmpty()) Triple(low, high, cycles)
            else {
                val next = flatMap { it.next() }
                val nextCycles = next.filter { !it.pulse && it.destination in parents }.fold(cycles) { acc, (_, destination) ->
                    acc + (destination to (acc[destination]?.let { pressed - it } ?: pressed))
                }
                next.propagate(low + next.count { !it.pulse }, high + next.count { it.pulse }, nextCycles)
            }

        val initial = State("", "broadcaster", false)
        return listOf(initial).propagate(1, 0, cycles)
    }

    override fun part1(input: String): Long {
        val modules = input.toModules()
        val (low, high) = (1..1000).fold(0L to 0L) { (low, high), _ ->
            pressButton(modules).let { (l, h) -> low + l to high + h }
        }
        return low * high
    }

    override fun part2(input: String): Long {
        val modules = input.toModules()
        val parents = modules.filterValues { parent -> modules.filterValues { "rx" in it.outputs }.keys.any { it in parent.outputs } }.keys

        tailrec fun minimize(pressed: Long = 1L, cycles: Map<String, Long> = mapOf()): Long =
            if (cycles.size == parents.size) cycles.values.reduce(Long::lcm)
            else {
                val (_, _, nextCycles) = pressButton(modules, parents, pressed, cycles)
                minimize(pressed + 1, nextCycles)
            }

        return minimize()
    }
}