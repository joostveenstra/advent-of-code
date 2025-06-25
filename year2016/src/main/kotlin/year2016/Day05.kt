package year2016

import framework.Context
import framework.Day
import java.security.MessageDigest
import kotlin.experimental.and

class Day05(context: Context) : Day by context {
    val md5: MessageDigest = MessageDigest.getInstance("MD5")
    val hexFormat = HexFormat { number { removeLeadingZeros = true } }

    fun String.toMd5(): ByteArray = md5.digest(toByteArray())

    val hashes = generateSequence(0) { it + 1 }
        .map { (input + it).toMd5() }
        .filter { it[0].toInt() == 0 && it[1].toInt() == 0 && it[2].toInt() and 0xF0 == 0 }

    fun part1() = hashes
        .take(8)
        .joinToString("") { (it[2] and 0x0F).toHexString(hexFormat) }

    fun part2() = hashes
        .filter { it[2] < 8 }
        .distinctBy { it[2] }
        .take(8)
        .sortedBy { it[2] }
        .joinToString("") { ((it[3].toInt() shr 4).toByte() and 0x0F).toHexString(hexFormat) }
}