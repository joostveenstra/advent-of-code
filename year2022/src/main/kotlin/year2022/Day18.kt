package year2022

import framework.Context
import framework.Day
import util.*

class Day18(context: Context) : Day by context {
    val cubes = lines.map { it.toPoint3D() }.toSet()

    fun Set<Point3D>.rangeOf(function: (Point3D) -> Int) = minOf(function) - 1..maxOf(function) + 1

    fun part1() = cubes.sumOf { it.cardinalNeighbours.count { n -> n !in cubes } }

    fun part2(): Int {
        val xs = cubes.rangeOf { it.x }
        val ys = cubes.rangeOf { it.y }
        val zs = cubes.rangeOf { it.z }

        val start = Point3D(xs.first, ys.first, zs.first)
        val queue = dequeOf(start)
        val visited = mutableSetOf(start)

        queue.drain { point ->
            point.cardinalNeighbours.filterNot { it in visited }.filterNot { it in cubes }.forEach { next ->
                if (next.x in xs && next.y in ys && next.z in zs) {
                    queue += next
                    visited += next
                }
            }
        }

        return cubes.sumOf { it.cardinalNeighbours.count { n -> n in visited } }
    }
}