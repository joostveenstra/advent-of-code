package year2023

import framework.Day
import util.*

object Day14 : Day<Int> {
    private val directions = listOf(NORTH, WEST, SOUTH, EAST)

    private fun String.toGrid() = lines().map { it.toCharArray() }.toTypedArray()

    private fun Array<CharArray>.tilt(direction: Point) = apply {
        when (direction) {
            NORTH ->
                for (y in indices) {
                    for (x in this[y].indices) {
                        if (this[y][x] == 'O') {
                            for (i in y downTo 1) {
                                if (this[i - 1][x] == '.') {
                                    this[i - 1][x] = 'O'
                                    this[i][x] = '.'
                                } else break
                            }
                        }
                    }
                }

            WEST ->
                for (y in indices) {
                    for (x in this[y].indices) {
                        if (this[y][x] == 'O') {
                            for (i in x downTo 1) {
                                if (this[y][i - 1] == '.') {
                                    this[y][i - 1] = 'O'
                                    this[y][i] = '.'
                                } else break
                            }
                        }
                    }
                }

            SOUTH ->
                for (y in indices.reversed()) {
                    for (x in this[y].indices) {
                        if (this[y][x] == 'O') {
                            for (i in y..<size - 1) {
                                if (this[i + 1][x] == '.') {
                                    this[i + 1][x] = 'O'
                                    this[i][x] = '.'
                                } else break
                            }
                        }
                    }
                }

            EAST ->
                for (y in indices) {
                    for (x in this[y].indices.reversed()) {
                        if (this[y][x] == 'O') {
                            for (i in x..<this[y].size - 1) {
                                if (this[y][i + 1] == '.') {
                                    this[y][i + 1] = 'O'
                                    this[y][i] = '.'
                                } else break
                            }
                        }
                    }
                }
        }
    }

    private fun Array<CharArray>.cycle() = directions.fold(this) { grid, direction -> grid.tilt(direction) }

    private fun Array<CharArray>.rocks() = flatMapIndexed { y, row ->
        row.asIterable().mapIndexedNotNull { x, c ->
            if (c == 'O') Point(x, y) else null
        }
    }

    private fun List<Point>.load(height: Int) = sumOf { (height - it.y) }

    override fun part1(input: String): Int {
        val grid = input.toGrid()
        return grid.tilt(NORTH).rocks().load(grid.size)
    }

    override fun part2(input: String): Int {
        val grid = input.toGrid()
        val seen = mutableMapOf(grid.rocks() to 0)

        while (true) {
            grid.cycle()
            val rocks = grid.rocks()
            if (rocks in seen) break
            seen += rocks to seen.size
        }

        val start = seen.getValue(grid.rocks())
        val end = seen.size
        val cycleWidth = end - start
        val offset = 1000000000 - start
        val remainder = offset % cycleWidth
        val target = start + remainder
        val (rocks) = seen.entries.first { (_, i) -> i == target }
        return rocks.load(grid.size)
    }
}