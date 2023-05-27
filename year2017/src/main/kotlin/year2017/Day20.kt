package year2017

import framework.Day
import util.Point3D
import util.allInts

object Day20 : Day<Int> {
    private val goal = Point3D(0, 0, 0)

    data class Particle(val position: Point3D, val velocity: Point3D, val acceleration: Point3D) {
        fun next() = Particle(position + velocity + acceleration, velocity + acceleration, acceleration)
    }

    private fun String.toParticles() = lines().map { line ->
        val (position, velocity, acceleration) = line.allInts().chunked(3).map { (x, y, z) -> Point3D(x, y, z) }.toList()
        Particle(position, velocity, acceleration)
    }

    override fun part1(input: String) = generateSequence(input.toParticles()) { particles -> particles.map { it.next() } }
        .drop(500).first().withIndex().minBy { it.value.position.manhattan(goal) }.index

    override fun part2(input: String) = generateSequence(input.toParticles()) { particles ->
        particles.map { it.next() }.groupBy { it.position }.values.filter { it.size == 1 }.flatten()
    }.drop(500).first().size
}