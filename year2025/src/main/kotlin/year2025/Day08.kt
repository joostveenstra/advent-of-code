package year2025

import framework.Context
import framework.Day
import util.*
import java.util.*

class Day08(context: Context) : Day by context {
    val boxes = lines.map { it.toPoint3D() }
    val uf = UnionFind<Point3D>().apply { addAll(boxes) }
    val comparator = compareBy<Pair<Point3D, Point3D>> { (a, b) -> a euclidean b }
    val queue = PriorityQueue(comparator).apply { addAll(boxes.pairs()) }
    val limit = if (isExample) 10 else 1000

    fun part1() = generateSequence { queue.poll() }
        .onEach { (a, b) -> uf.union(a, b) }
        .nth(limit - 1)
        .let { uf.sizes.sorted().takeLast(3).product() }

    fun part2() = generateSequence { queue.poll() }
        .onEach { (a, b) -> uf.union(a, b) }
        .dropWhile { uf.numberOfSets > 1 }
        .first().let { (a, b) -> a.x.toLong() * b.x }
}