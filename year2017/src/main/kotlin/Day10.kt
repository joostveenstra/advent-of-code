import java.util.HexFormat

object Day10 : Day<Any> {
    private fun String.toSize() = if (length < 8) 5 else 256

    private fun List<Int>.knotHash(size: Int): List<Int> {
        val initial = (0 until size).toList() to 0
        val (numbers, position) = zipWithIndex().fold(initial) { (numbers, position), (length, skip) ->
            val next = numbers.take(length).reversed() + numbers.drop(length)
            val offset = (length + skip) % size
            next.drop(offset) + next.take(offset) to (position - offset).mod(size)
        }
        return numbers.drop(position) + numbers.take(position)
    }

    override fun part1(input: String): Int {
        val lengths = input.split(',').map { it.toInt() }
        return lengths.knotHash(input.toSize()).take(2).product()
    }

    override fun part2(input: String): String {
        val lengths = input.map { it.code } + listOf(17, 31, 73, 47, 23)
        val repeated = List(64) { lengths }.flatten()
        return repeated.knotHash(256).chunked(16).map { it.reduce(Int::xor).toByte() }.let { HexFormat.of().formatHex(it.toByteArray()) }
    }
}