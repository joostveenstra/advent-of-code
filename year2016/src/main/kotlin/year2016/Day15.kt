package year2016

import framework.Day
import util.allInts

object Day15 : Day {
    data class Disc(val id: Int, val size: Int, val offset: Int)

    private fun Disc.pass(time: Int) = (time + id + offset) % size == 0

    private fun String.toDisks() = lines().map { line ->
        val (id, size, _, offset) = line.allInts().toList()
        Disc(id, size, offset)
    }

    private fun firstCapsule(disks: List<Disc>) = generateSequence(0) { it + 1 }.first { time -> disks.all { it.pass(time) } }

    override fun part1(input: String) = firstCapsule(input.toDisks())

    override fun part2(input: String): Int {
        val discs = input.toDisks()
        val expanded = discs + Disc(discs.size + 1, 11, 0)
        return firstCapsule(expanded)
    }
}