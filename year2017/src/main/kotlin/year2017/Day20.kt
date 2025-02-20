package year2017

import framework.Context
import framework.Day
import util.*

class Day20(context: Context) : Day by context {
    data class Particle(val position: Point3D, val velocity: Point3D, val acceleration: Point3D) {
        fun next() = Particle(position + velocity + acceleration, velocity + acceleration, acceleration)
    }

    val goal = Point3D(0, 0, 0)
    val particles = lines.map { line ->
        val (position, velocity, acceleration) = line.allInts().chunked(3).map { (x, y, z) -> Point3D(x, y, z) }.toList()
        Particle(position, velocity, acceleration)
    }

    fun part1() = generateSequence(particles) { particles ->
        particles.map { it.next() }
    }.nth(500).withIndex().minBy { it.value.position.manhattan(goal) }.index

    fun part2() = generateSequence(particles) { particles ->
        particles.map { it.next() }.groupBy { it.position }.values.filter { it.size == 1 }.flatten()
    }.nth(500).size
}