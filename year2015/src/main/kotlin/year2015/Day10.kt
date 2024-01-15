package year2015

import framework.Day

object Day10 : Day<Int> {
    private fun next(it: String) = buildString {
        (it + 'x').fold(0 to it.first()) { (count, current), next ->
            if (current == next) {
                count + 1 to current
            } else {
                append(count).append(current)
                1 to next
            }
        }
    }

    private fun String.generate(turns: Int) = generateSequence(this, ::next).drop(turns).first().length

    override fun part1(input: String) = input.generate(40)

    override fun part2(input: String) = input.generate(50)
}