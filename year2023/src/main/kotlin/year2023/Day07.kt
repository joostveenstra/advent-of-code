package year2023

import framework.Context
import framework.Day

class Day07(context: Context) : Day by context {
    enum class Type { HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND }

    data class Hand(val cards: List<Int>, val type: Type, val bid: Int) : Comparable<Hand> {
        override fun compareTo(other: Hand) = compareValuesBy(this, other,
            Hand::type, { it.cards[0] }, { it.cards[1] }, { it.cards[2] }, { it.cards[3] }, { it.cards[4] }
        )
    }

    fun String.toHands(jokers: Boolean) = lines().map { line ->
        val (cards, bid) = line.split(' ')
        Hand(cards.map { it.strength(jokers) }, cards.toType(jokers), bid.toInt())
    }

    fun String.toType(jokers: Boolean): Type {
        val groups = groupingBy { it }.eachCount()
        val replaced = if (jokers && 'J' in groups) {
            val max = groups.filterKeys { it != 'J' }.maxByOrNull { it.value }?.key
            max?.let { groups + (it to groups.getValue(it) + groups.getValue('J')) - 'J' } ?: groups
        } else groups

        return when (replaced.size) {
            1 -> Type.FIVE_OF_A_KIND
            2 -> if (replaced.containsValue(4)) Type.FOUR_OF_A_KIND else Type.FULL_HOUSE
            3 -> if (replaced.containsValue(3)) Type.THREE_OF_A_KIND else Type.TWO_PAIR
            4 -> Type.ONE_PAIR
            else -> Type.HIGH_CARD
        }
    }

    fun Char.strength(jokers: Boolean) = when (this) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> if (jokers) 1 else 11
        'T' -> 10
        else -> digitToInt()
    }

    fun List<Hand>.winnings() = sorted().withIndex().sumOf { (index, hand) -> (index + 1) * hand.bid }

    fun part1() = input.toHands(false).winnings()
    fun part2() = input.toHands(true).winnings()
}