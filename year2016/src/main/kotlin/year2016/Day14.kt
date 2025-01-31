package year2016

import framework.Day
import util.find
import util.nth
import java.security.MessageDigest
import java.util.*

object Day14 : Day {
    private val md5 = MessageDigest.getInstance("MD5")
    private val three = "(.)\\1{2}".toRegex()
    private val five = "(.)\\1{4}".toRegex()

    private fun String.toMd5() = HexFormat.of().formatHex(md5.digest(toByteArray()))
    private fun String.toMd5Stretched() = generateSequence(toMd5()) { it.toMd5() }.nth(2016)

    private fun isValid(window: IndexedValue<List<String>>) =
        window.value.first().find(three)?.let { (t) ->
            window.value.drop(1).any {
                it.find(five)?.let { (f) -> t == f } ?: false
            }
        } ?: false

    private fun generateKey64(hash: (Int) -> String) =
        generateSequence(0) { it + 1 }.map(hash).windowed(1001).withIndex().filter(::isValid).nth(63).index

    override fun part1(input: String) = generateKey64 { "$input$it".toMd5() }

    override fun part2(input: String) = generateKey64 { "$input$it".toMd5Stretched() }
}