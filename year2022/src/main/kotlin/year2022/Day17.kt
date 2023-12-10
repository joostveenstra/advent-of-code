package year2022

import framework.Day
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toPersistentHashSet
import util.LEFT
import util.Point
import util.RIGHT

typealias Shape = List<Point>

object Day17 : Day<Long> {
    private val rocks: List<Shape> = listOf(
        listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(3, 0)),
        listOf(Point(0, 1), Point(1, 1), Point(2, 1), Point(1, 0), Point(1, 2)),
        listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(2, 1), Point(2, 2)),
        listOf(Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3)),
        listOf(Point(0, 0), Point(1, 0), Point(0, 1), Point(1, 1))
    )

    data class State(val grid: PersistentSet<Point>, val jets: String, val shapeIndex: Int, val jetIndex: Int, val height: Int)

    private fun Shape.move(other: Point) = map { it + other }
    private fun Shape.canMove(grid: Set<Point>) = all { it.x in (0..6) && it !in grid }

    private tailrec fun Shape.fall(grid: Set<Point>, jets: String, jetIndex: Int): Pair<List<Point>, Int> {
        val jet = jets[jetIndex % jets.length]
        val shifted = if (jet == '>') move(RIGHT) else move(LEFT)
        val next = if (shifted.canMove(grid)) shifted else this
        val down = next.move(Point(0, -1))
        return if (down.canMove(grid)) down.fall(grid, jets, jetIndex + 1) else (next to jetIndex + 1)
    }

    private fun State.next(): State {
        val next = rocks[shapeIndex % rocks.size].move(Point(2, height + 4))
        val (nextShape, nextJetIndex) = next.fall(grid, jets, jetIndex)
        val nextHeight = maxOf(height, nextShape.maxOf { it.y })
        return State(grid + nextShape, jets, shapeIndex + 1, nextJetIndex, nextHeight)
    }

    private fun simulate(jets: String): Sequence<Int> {
        val grid = buildSet { (0..6).map { add(Point(it, 0)) } }.toPersistentHashSet()
        val initial = State(grid, jets, 0, 0, 0)
        return generateSequence(initial) { it.next() }.map { it.height }
    }

    override fun part1(input: String) = simulate(input).drop(2022).first().toLong()

    override fun part2(input: String): Long {
        val guess = 1000
        val height = simulate(input).drop(1).take(5 * guess).toList()
        val delta = height.windowed(2).map { (a, b) -> b - a }
        val end = delta.size - guess
        val start = delta.slice(0 until end).windowed(guess).indexOfLast { it == delta.takeLast(guess) }
        val cycleHeight = height[end] - height[start]
        val cycleWidth = end - start
        val offset = 1_000_000_000_000L - 1 - start
        val quotient = offset / cycleWidth
        val remainder = offset % cycleWidth
        return (quotient * cycleHeight) + height[start + remainder.toInt()]
    }
}