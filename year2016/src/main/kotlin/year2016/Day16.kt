package year2016

import framework.Day

object Day16 : Day<String> {
    private fun String.toGoal() = if (this == "10000") 20 else 272

    private fun String.checksum() = chunkedSequence(length.takeLowestOneBit()).joinToString("") { chunk ->
        if (chunk.count { it == '1' } % 2 == 0) "1" else "0"
    }

    private fun String.fill(goal: Int): String {
        fun step(a: String) = a + '0' + a.reversed().asIterable().joinToString("") { if (it == '1') "0" else "1" }
        return generateSequence(this, ::step).dropWhile { it.length < goal }.first().take(goal)
    }

    override fun part1(input: String) = input.fill(input.toGoal()).checksum()

    override fun part2(input: String) = input.fill(35651584).checksum()
}