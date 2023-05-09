object Day22 : Day<Int> {
    private val spells = setOf(MagicMissile, Drain, Shield, Poison, Recharge)

    sealed class Spell(val cost: Int, val duration: Int)
    object MagicMissile : Spell(53, 1)
    object Drain : Spell(73, 1)
    object Shield : Spell(113, 6)
    object Poison : Spell(173, 6)
    object Recharge : Spell(229, 5)

    data class State(
        val you: Int, val mana: Int, val spent: Int, val armor: Int,
        val boss: Int, val damage: Int,
        val active: Map<Spell, Int>,
        val bossTurn: Boolean
    )

    private fun String.fight(hard: Boolean): Int {
        val (hp, damage) = lines().map { line -> line.filter { it.isDigit() }.toInt() }
        val initial = State(50, 500, 0, 0, hp, damage, mapOf(), false)
        val stack = dequeOf(initial)
        var best = Int.MAX_VALUE

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
            else next.copy(active = state.active - spell + (spell to duration - 1))
        }

        fun State.youCast(spell: Spell) = copy(mana = mana - spell.cost, spent = spent + spell.cost, active = active + (spell to spell.duration), bossTurn = true)
        fun State.bossAttacks() = copy(you = you - maxOf(1, damage - armor), bossTurn = false)
        fun State.hard() = copy(you = you - 1)

        fun State.youWin() = boss <= 0
        fun State.bossWins() = you <= 0
        fun State.canBeatBest() = spent < best

        fun State.nextStates(): List<State> =
            if (bossTurn) listOf(bossAttacks())
            else (spells - active.keys).filter { it.cost < mana }.map { youCast(it) }

        while (stack.isNotEmpty()) {
            stack.pop()
                .let { if (hard) it.hard() else it }
                .applyEffects()
                .run {
                    when {
                        bossWins() || !canBeatBest() -> Unit
                        youWin() -> best = minOf(best, spent)
                        else -> nextStates().forEach {
                            stack.push(it)
                        }
                    }
                }
        }

        return best
    }

    override fun part1(input: String) = input.fight(false)

    override fun part2(input: String) = input.fight(true)
}