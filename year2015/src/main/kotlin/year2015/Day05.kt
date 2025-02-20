package year2015

import framework.Context
import framework.Day

class Day05(context: Context) : Day by context {
    val vowels = "[aeiou].*[aeiou].*[aeiou]".toRegex()
    val pair = "(.)\\1".toRegex()
    val naughty = "ab|cd|pq|xy".toRegex()

    val pairs = "(..).*\\1".toRegex()
    val split = "(.).\\1".toRegex()

    fun part1() = lines.count { vowels in it && pair in it && naughty !in it }
    fun part2() = lines.count { pairs in it && split in it }
}