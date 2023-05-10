import kotlin.math.absoluteValue

object Day11 : Day<Int> {
    data class Hex(val q: Int, val r: Int) {
        private val s: Int get() = -q - r
        fun distance() = maxOf(q.absoluteValue, r.absoluteValue, s.absoluteValue)
        fun step(direction: String) = when (direction) {
            "n" -> Hex(q + 1, r - 1)
            "s" -> Hex(q - 1, r + 1)
            "ne" -> Hex(q + 1, r)
            "sw" -> Hex(q - 1, r)
            "se" -> Hex(q, r + 1)
            else -> Hex(q, r - 1)
        }
    }

    private fun String.walk() = split(',').scan(Hex(0, 0), Hex::step).map { it.distance() }

    override fun part1(input: String) = input.walk().last()

    override fun part2(input: String) = input.walk().max()
}