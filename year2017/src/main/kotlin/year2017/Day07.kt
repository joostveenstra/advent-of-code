package year2017

import framework.Context
import framework.Day

class Day07(context: Context) : Day by context {
    data class Program(val name: String, val weight: Int, val total: Int, val unbalanced: Boolean, val children: List<Program>)

    val tower = run {
        val regex = "[(), ]+".toRegex()
        val weights = lines.associate { line -> line.split(regex).let { (name, weight) -> name to weight.toInt() } }
        val edges = lines.associate { line -> line.split(regex).let { it.first() to it.drop(3) } }

        fun buildTower(name: String): Program {
            val weight = weights.getValue(name)
            val children = edges.getValue(name).map(::buildTower)
            val total = weight + children.sumOf { it.total }
            val unbalanced = children.any { it.total != children.first().total }
            return Program(name, weight, total, unbalanced, children)
        }

        val nonRoot = edges.values.flatten().toSet()
        val root = edges.keys.first { it !in nonRoot }
        buildTower(root)
    }

    fun Program.findUnbalancedWeight(): Int = children.find { it.unbalanced }
        ?.findUnbalancedWeight()
        ?: children.partition { it.total == children.first().total }.let { (left, right) ->
            val (l) = left
            val (r) = right
            if (left.size == 1) l.weight + r.total - l.total else r.weight + l.total - r.total
        }

    fun part1() = tower.name
    fun part2() = tower.findUnbalancedWeight()
}