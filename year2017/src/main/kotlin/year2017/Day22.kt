package year2017

import framework.Context
import framework.Day
import util.*

class Day22(context: Context) : Day by context {
    val initial = lines.toGrid { it == '#' }
    val start = Point(256, 256)
    val offset = 256 - initial.width / 2
    val grid = buildIntGrid(512, 512, 1) {
        initial.points.filter { initial[it] }.forEach { this[it + Point(offset, offset)] = 3 }
    }

    tailrec fun MutableIntGrid.move(position: Point, direction: Int, bursts: Int, step: Int, infected: Int = 0): Int =
        if (bursts == 0) infected
        else {
            val current = this[position]
            val next = (current + step) and 3 // % 4
            val nextDirection = (direction + current + 2) and 3 // % 4
            this[position] = next
            move(position + cardinal[nextDirection], nextDirection, bursts - 1, step, infected + ((next + 1) shr 2)) // if (next == 3) 1 else 0
        }

    fun move(bursts: Int, step: Int) = grid.toMutableGrid().move(start, cardinal.indexOf(UP), bursts, step)

    fun part1(): Int = move(10000, 2)
    fun part2(): Int = move(10000000, 1)
}