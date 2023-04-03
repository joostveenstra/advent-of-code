import java.security.MessageDigest

object Day4 : Day<Int> {
    private val md5 = MessageDigest.getInstance("MD5")

    private fun md5(string: String) = md5.digest(string.toByteArray())

    private fun String.find(predicate: (ByteArray) -> Boolean) =
        generateSequence(1) { it + 1 }.indexOfFirst { index -> predicate(md5(this + index)) } + 1

    override fun part1(input: String) = input.find { it[0].toInt() == 0 && it[1].toInt() == 0 && it[2].toInt() and 0xF0 == 0 }

    override fun part2(input: String) = input.find { it[0].toInt() == 0 && it[1].toInt() == 0 && it[2].toInt() == 0 }
}