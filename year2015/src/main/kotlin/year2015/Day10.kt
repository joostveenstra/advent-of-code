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

    fun generate(turns: Int) = generateSequence(input, ::next).nth(turns).length

    fun part1() = generate(40)
    fun part2() = generate(50)
}