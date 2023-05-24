package year2016

import framework.Day
import java.security.MessageDigest
import kotlin.experimental.and

object Day5 : Day<String> {
    private val md5 = MessageDigest.getInstance("MD5")

    private fun String.toMd5() = md5.digest(toByteArray())

    private fun Byte.toHexString(): String = toUByte().toString(radix = 16)

    private fun String.generateHashes() =
        generateSequence(0) { it + 1 }.map { (this + it).toMd5() }.filter { it[0].toInt() == 0 && it[1].toInt() == 0 && it[2].toInt() and 0xF0 == 0 }

    override fun part1(input: String) = input.generateHashes().take(8)
        .joinToString("") { (it[2] and 0x0F).toHexString() }

    override fun part2(input: String) = input.generateHashes().filter { it[2] < 8 }.distinctBy { it[2] }.take(8).sortedBy { it[2] }
        .joinToString("") { ((it[3].toInt() shr 4).toByte() and 0x0F).toHexString() }
}