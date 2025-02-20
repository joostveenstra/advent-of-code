package year2015

import framework.Context
import framework.Day
import util.pairs

class Day21(context: Context) : Day by context {
    data class Item(val cost: Int, val damage: Int, val armor: Int) {
        operator fun plus(other: Item) = Item(cost + other.cost, damage + other.damage, armor + other.armor)
        fun beats(boss: Item): Boolean {
            val bossHP = boss.cost / maxOf(1, damage - boss.armor)
            val youHP = 100 / maxOf(1, boss.damage - armor)
            return youHP >= bossHP
        }
    }

    val none = Item(0, 0, 0)
    val weapons = listOf(
        Item(8, 4, 0),
        Item(10, 5, 0),
        Item(25, 6, 0),
        Item(40, 7, 0),
        Item(74, 8, 0)
    )
    val armors = listOf(
        none,
        Item(13, 0, 1),
        Item(31, 0, 2),
        Item(53, 0, 3),
        Item(75, 0, 4),
        Item(102, 0, 5)
    )
    val rings = listOf(
        none,
        none,
        Item(25, 1, 0),
        Item(50, 2, 0),
        Item(100, 3, 0),
        Item(20, 0, 1),
        Item(40, 0, 2),
        Item(80, 0, 3)
    ).pairs().map { (a, b) -> a + b }

    val equipment = buildList { for (weapon in weapons) for (armor in armors) for (pairOfRings in rings) add(weapon + armor + pairOfRings) }

    val boss = lines.map { line -> line.filter { it.isDigit() }.toInt() }.let { Item(it[0], it[1], it[2]) }

    fun part1() = equipment.filter { it.beats(boss) }.minOf { it.cost }
    fun part2() = equipment.filterNot { it.beats(boss) }.maxOf { it.cost }
}