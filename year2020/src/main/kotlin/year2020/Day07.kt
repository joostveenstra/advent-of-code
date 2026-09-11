package year2020

import framework.Context
import framework.Day

class Day07(context: Context) : Day by context {
    data class Rule(val amount: Int, val bag: String)

    val bags = lines.associate { l ->
        val tokens = l.split(" ").windowed(4, 4, false)
        val (first, second) = tokens.first()
        val key = first + second
        val rules = tokens.drop(1).map { (amount, first, second) -> Rule(amount.toInt(), first + second) }
        key to rules
    }

    val cacheContains = mutableMapOf<String, Boolean>()
    fun String.containsShinyGold(): Boolean = cacheContains.getOrPut(this) {
        bags.getValue(this).any { it.bag == "shinygold" || it.bag.containsShinyGold() }
    }

    val cacheCount = mutableMapOf<String, Int>()
    fun String.countBags(): Int = cacheCount.getOrPut(this) {
        1 + bags.getValue(this).sumOf { it.amount * it.bag.countBags() }
    }

    fun part1() = bags.keys.count { it.containsShinyGold() }
    fun part2() = "shinygold".countBags() - 1
}