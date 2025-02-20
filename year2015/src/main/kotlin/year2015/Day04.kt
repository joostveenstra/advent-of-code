package year2015

import framework.Context
import framework.Day
import java.security.MessageDigest

class Day04(context: Context) : Day by context {
    val md5: MessageDigest = MessageDigest.getInstance("MD5")

    fun String.toMd5(): ByteArray = md5.digest(toByteArray())

    fun String.find(predicate: (ByteArray) -> Boolean) =
        generateSequence(1) { it + 1 }.indexOfFirst { index -> predicate((this + index).toMd5()) } + 1

    fun part1() = input.find { it[0].toInt() == 0 && it[1].toInt() == 0 && it[2].toInt() and 0xF0 == 0 }
    fun part2() = input.find { it[0].toInt() == 0 && it[1].toInt() == 0 && it[2].toInt() == 0 }
}