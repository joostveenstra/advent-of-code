package year2015

import framework.Day
import util.combinations

object Day21 : Day<Int> {
    private val none = Item(0, 0, 0)
    private val weapons = listOf(
        Item(8, 4, 0),
        Item(10, 5, 0),
        Item(25, 6, 0),
        Item(40, 7, 0),
        Item(74, 8, 0)
    )
    private val armors = listOf(
        none,
        Item(13, 0, 1),
        Item(31, 0, 2),
        Item(53, 0, 3),
        Item(75, 0, 4),
        Item(102, 0, 5)
    )
    private val rings = listOf(
        none,
        none,
        Item(25, 1, 0),
        Item(50, 2, 0),
        Item(100, 3, 0),
        Item(20, 0, 1),
        Item(40, 0, 2),
        Item(80, 0, 3)
    ).combinations(2).map { (a, b) -> a + b }

    private val equipment = buildList { for (weapon in weapons) for (armor in armors) for (pairOfRings in rings) add(weapon + armor + pairOfRings) }

    data class Item(val cost: Int, val damage: Int, val armor: Int) {
        operator fun plus(other: Item) = Item(cost + other.cost, damage + other.damage, armor + other.armor)
    }

    private fun String.toBoss() = lines().map { line -> line.filter { it.isDigit() }.toInt() }.let { (cost, damage, armor) -> Item(cost, damage, armor) }

    private fun Item.beats(boss: Item): Boolean {
        val bossHP = boss.cost / maxOf(1, damage - boss.armor)
        val youHP = 100 / maxOf(1, boss.damage - armor)
        return youHP >= bossHP
    }

    override fun part1(input: String): Int {
        val boss = input.toBoss()
        return equipment.filter { it.beats(boss) }.minOf { it.cost }
    }

    override fun part2(input: String): Int {
        val boss = input.toBoss()
        return equipment.filterNot { it.beats(boss) }.maxOf { it.cost }
    }
}