package year2016

import framework.Context
import framework.Day
import util.allInts

class Day15(context: Context) : Day by context {
    data class Disc(val id: Int, val size: Int, val offset: Int)

    val discs = lines.map { line ->
        val (id, size, _, offset) = line.allInts().toList()
        Disc(id, size, offset)
    }
    val expanded = discs + Disc(discs.size + 1, 11, 0)

    fun Disc.pass(time: Int) = (time + id + offset) % size == 0

    fun firstCapsule(disks: List<Disc>) = generateSequence(0) { it + 1 }.first { time -> disks.all { it.pass(time) } }

    fun part1() = firstCapsule(discs)
    fun part2() = firstCapsule(expanded)
}