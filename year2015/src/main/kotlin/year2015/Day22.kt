package year2015

import framework.Context
import framework.Day
import util.drain
import util.priorityQueueOf

class Day22(context: Context) : Day by context {
    sealed class Spell(val cost: Int, val duration: Int)
    data object MagicMissile : Spell(53, 1)
    data object Drain : Spell(73, 1)
    data object Shield : Spell(113, 6)
    data object Poison : Spell(173, 6)
    data object Recharge : Spell(229, 5)

    val spells = listOf(MagicMissile, Drain, Shield, Poison, Recharge)

    data class State(
        val you: Int, val mana: Int, val spent: Int, val armor: Int,
        val boss: Int, val damage: Int,
        val active: Map<Spell, Int>,
        val bossTurn: Boolean
    )

    fun String.fight(hard: Boolean): Int {
        val (hp, damage) = lines().map { line -> line.filter { it.isDigit() }.toInt() }
        val initial = State(50, 500, 0, 0, hp, damage, mapOf(), false)
        val queue = priorityQueueOf(initial) { it.spent }
        val seen = mutableSetOf(initial)

        fun State.effect(spell: Spell) = when (spell) {
            MagicMissile -> copy(boss = boss - 4)
            Drain -> copy(you = you + 2, boss = boss - 2)
            Shield -> copy(armor = 7)
            Poison -> copy(boss = boss - 3)
            Recharge -> copy(mana = mana + 101)
        }

        fun State.applyEffects(): State = active.entries.fold(copy(armor = 0)) { state, (spell, duration) ->
            val next = state.effect(spell)
            if (duration == 1) next.copy(active = state.active - spell)
            else next.copy(active = state.active + (spell to duration - 1))
        }

        fun State.youCast(spell: Spell) = copy(mana = mana - spell.cost, spent = spent + spell.cost, active = active + (spell to spell.duration), bossTurn = true)
        fun State.bossAttacks() = copy(you = you - maxOf(1, damage - armor), bossTurn = false)
        fun State.hard() = copy(you = you - 1)

        fun State.youWin() = boss <= 0
        fun State.bossWins() = you <= 0 || mana < 53

        fun State.nextStates(): List<State> =
            if (bossTurn) listOf(bossAttacks())
            else (spells - active.keys).filter { it.cost < mana }.map { youCast(it) }

        queue.drain {
            (if (hard) it.hard() else it)
                .applyEffects()
                .run {
                    when {
                        bossWins() -> Unit
                        youWin() -> return spent
                        else -> nextStates().forEach { next -> if (seen.add(next)) queue.add(next) }
                    }
                }
        }

        error("This should never happen")
    }

    fun part1() = input.fight(false)
    fun part2() = input.fight(true)
}