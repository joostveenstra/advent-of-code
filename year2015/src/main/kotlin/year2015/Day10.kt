package year2015

import framework.Context
import framework.Day
import util.nth

class Day10(context: Context) : Day by context {
    fun next(it: String) = buildString {
        (it + 'x').fold(0 to it.first()) { (count, current), next ->
            if (current == next) {
                count + 1 to current
            } else {
                append(count).append(current)
                1 to next
            }
        }
    }

    fun String.generate(turns: Int) = generateSequence(this, ::next).nth(turns).length

    fun part1() = input.generate(40)
    fun part2() = input.generate(50)
}