package year2023

import framework.Day
import util.Point
import util.toCharGrid

object Day21 : Day<Long> {
    override fun part1(input: String): Long {
        val grid = input.toCharGrid()
        val start = grid.find('S')

        tailrec fun Set<Point>.walk(steps: Int): Int =
            if (steps == 0) size
            else flatMap { it.cardinalNeighbours }
                .filter { grid.safeGet(it) != '#' }
                .toSet()
                .walk(steps - 1)

        return setOf(start).walk(64).toLong()
    }

    override fun part2(input: String): Long {
        val grid = input.toCharGrid()
        val start = grid.find('S')

        val grids = 26501365 / grid.height
        val remainder = 26501365 % grid.height

        fun Point.wrap(size: Int) = Point(x.mod(size), y.mod(size))

        tailrec fun Set<Point>.walk(steps: Int): Set<Point> =
            if (steps == 0) this
            else flatMap { it.cardinalNeighbours }
                .filter { grid[it.wrap(grid.height)] != '#' }
                .toSet()
                .walk(steps - 1)

        // As we can walk in a straight line from start without obstacles in any direction the result can be calculated using a quadratic equation:
        // u_n = a*n^2 + b*n + c
        // To solve the equation we need to calculate the first three terms (n=0..2) in the sequence (remainder + n * grid.height)
        tailrec fun Set<Point>.sequence(n: Int = 0, sequence: List<Int> = listOf()): List<Int> =
            if (n == 3) sequence
            else {
                val step = walk(if (n == 0) remainder else grid.height)
                step.sequence(n + 1, sequence + step.size)
            }

        val (u0, u1, u2) = setOf(start).sequence()

        // We can rewrite the terms to the following equations:
        // u_0 = c -> c = u_0
        // u_1 = a + b + c -> a + b = u1 - c
        // u_2 = 4a + 2b + c -> 4a + 2b = u_2 - c
        // 2a = 4a + 2b - 2(a + b)
        val c = u0
        val aPlusB = u1 - c
        val fourAPlusTwoB = u2 - c
        val twoA = fourAPlusTwoB - (2 * aPlusB)
        val a = twoA / 2
        val b = aPlusB - a

        return grids.toLong().let { n -> a * (n * n) + b * n + c }
    }
}