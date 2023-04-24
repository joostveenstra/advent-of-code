import java.security.MessageDigest
import java.util.*

object Day14 : Day<Int> {
    private val md5 = MessageDigest.getInstance("MD5")
    private val three = "(.)\\1{2}".toRegex()
    private val five = "(.)\\1{4}".toRegex()

    private fun String.toMd5() = HexFormat.of().formatHex(md5.digest(toByteArray()))
    private fun String.toMd5Stretched() = generateSequence(toMd5()) { it.toMd5() }.drop(2016).first()

    private fun isValid(window: List<IndexedValue<String>>) =
        window.first().value.find(three)?.let { (t) ->
            window.drop(1).any {
                it.value.find(five)?.let { (f) -> t == f } ?: false
            }
        } ?: false

    private fun generateKey64(hash: (Int) -> String) =
        generateSequence(0) { it + 1 }.map(hash).withIndex().windowed(1001).filter(::isValid).drop(63).first().first().index

    override fun part1(input: String) = generateKey64 { "$input$it".toMd5() }

    override fun part2(input: String) = generateKey64 { "$input$it".toMd5Stretched() }
}