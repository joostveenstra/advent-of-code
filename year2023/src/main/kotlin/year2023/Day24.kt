package year2023

import framework.Day
import util.allLongs
import util.pairs
import java.math.BigInteger

object Day24 : Day {
    val range = 200_000_000_000_000..400_000_000_000_000

    data class HailStone(val x: Long, val y: Long, val z: Long, val vx: Long, val vy: Long, val vz: Long)

    data class Vector(val x: BigInteger, val y: BigInteger, val z: BigInteger) {
        constructor(x: Long, y: Long, z: Long) : this(x.toBigInteger(), y.toBigInteger(), z.toBigInteger())
        operator fun plus(v: Vector) = Vector(x + v.x, y + v.y, z + v.z)
        operator fun minus(v: Vector) = Vector(x - v.x, y - v.y, z - v.z)
        infix fun cross(v: Vector) = Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x)
        fun sum() = x + y + z
    }

    private fun String.toHailStones() = allLongs().chunked(6).map { HailStone(it[0], it[1], it[2], it[3], it[4], it[5]) }.toList()

    override fun part1(input: String) = input.toHailStones().pairs().count { (a, b) ->
        val determinant = a.vy * b.vx - a.vx * b.vy
        if (determinant == 0L) false else {
            val t = (b.vx * (b.y - a.y) - b.vy * (b.x - a.x)) / determinant
            val u = (a.vx * (b.y - a.y) - a.vy * (b.x - a.x)) / determinant

            val x = a.x + t * a.vx
            val y = a.y + t * a.vy

            t >= 0 && u >= 0 && x in range && y in range
        }
    }

    override fun part2(input: String) = with(input.toHailStones()) {
        fun HailStone.asVector() = Vector(x, y, z) to Vector(vx, vy, vz)

        val (p0, v0) = get(0).asVector()
        val (p1, v1) = get(1).asVector().let { (p, v) -> p - p0 to v - v0 }
        val (p2, v2) = get(2).asVector().let { (p, v) -> p - p0 to v - v0 }

        val q = v1 cross p1
        val r = v2 cross p2
        val s = q cross r

        val t = (p1.y * s.x - p1.x * s.y) / (v1.x * s.y - v1.y * s.x)
        val u = (p2.y * s.x - p2.x * s.y) / (v2.x * s.y - v2.y * s.x)

        val a = (p0 + p1).sum()
        val b = (p0 + p2).sum()
        val c = (v1 - v2).sum()

        (u * a - t * b + u * t * c) / (u - t)
    }
}