package year2025

import framework.Context
import framework.Day

class Day01(context: Context) : Day by context {
    val start = 50
    val rotations = lines.map {
        val direction = it.first()
        val distance = it.drop(1).toInt()
        if (direction == 'R') distance else -distance
    }

    fun Int.rotate(n: Int) = (this + n).mod(100)

    fun Int.countZeros(n: Int) =
        if (n >= 0) {
            (this + n) / 100
        } else {
            val reversed = (100 - this) % 100
            (reversed - n) / 100
        }

    fun part1() = rotations.scan(start) { dial, n -> dial.rotate(n) }.count { it == 0 }
    fun part2() = rotations.fold(start to 0) { (dial, password), n -> dial.rotate(n) to password + dial.countZeros(n) }.second
}