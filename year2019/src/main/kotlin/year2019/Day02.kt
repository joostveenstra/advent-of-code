package year2019

import framework.Context
import framework.Day
import util.allInts
import util.permPairs

class Day02(context: Context) : Day by context {
    val program = input.allInts().toList()

    fun run(verb: Int, noun: Int) = with(program.toMutableList()) {
        this[1] = verb
        this[2] = noun

        for (i in indices step 4) {
            val opcode = this[i]
            val input1 = this[i + 1]
            val input2 = this[i + 2]
            val output = this[i + 3]

            when (opcode) {
                1 -> this[output] = this[input1] + this[input2]
                2 -> this[output] = this[input1] * this[input2]
                else -> break
            }
        }

        this[0]
    }

    fun part1(): Int = run(12, 2)
    fun part2(): Int {
        val (verb, noun) = (0..99).permPairs().first { (verb, noun) -> run(verb, noun) == 19690720 }
        return 100 * verb + noun
    }
}