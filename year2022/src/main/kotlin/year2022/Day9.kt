package year2022
import framework.Day
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.plus
import util.*
import kotlin.math.absoluteValue
import kotlin.math.sign

object Day9 : Day {
    private fun String.toMoves() = lines().flatMap {
        it.split(' ').let { (direction, steps) ->
            List(steps.toInt()) { direction.first().toDirection() }
        }
    }

    private infix fun Point.touches(other: Point) = (x - other.x).absoluteValue <= 1 && (y - other.y).absoluteValue <= 1
    private infix fun Point.moveTowards(other: Point) = Point(x + (other.x - x).sign, y + (other.y - y).sign)

    private fun List<Direction>.simulate(size: Int): Int {
        val start = ORIGIN
        val initial = List(size) { start } to persistentListOf(start)
        val (_, visited) = this.fold(initial) { (rope, visited), move ->
            val nextHead = rope.first() + move
            val nextRope = rope.drop(1).scan(nextHead) { head, tail ->
                if (tail touches head) tail else tail moveTowards head
            }
            nextRope to visited + nextRope.last()
        }
        return visited.toSet().size
    }

    override fun part1(input: String): Int = input.toMoves().simulate(2)

    override fun part2(input: String): Int = input.toMoves().simulate(10)
}