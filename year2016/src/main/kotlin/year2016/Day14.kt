package year2016

import framework.Context
import framework.Day
import util.find
import util.nth
import java.security.MessageDigest
import java.util.*

class Day14(context: Context) : Day by context {
    val md5: MessageDigest = MessageDigest.getInstance("MD5")
    val three = "(.)\\1{2}".toRegex()
    val five = "(.)\\1{4}".toRegex()

    fun String.toMd5(): String = HexFormat.of().formatHex(md5.digest(toByteArray()))
    fun String.toMd5Stretched() = generateSequence(toMd5()) { it.toMd5() }.nth(2016)

    fun isValid(window: IndexedValue<List<String>>) =
        window.value.first().find(three)?.let { (t) ->
            window.value.drop(1).any {
                it.find(five)?.let { (f) -> t == f } ?: false
            }
        } ?: false

    fun generateKey64(hash: (Int) -> String) =
        generateSequence(0) { it + 1 }.map(hash).windowed(1001).withIndex().filter(::isValid).nth(63).index

    fun part1() = generateKey64 { "${input}$it".toMd5() }
    fun part2() = generateKey64 { "${input}$it".toMd5Stretched() }
}